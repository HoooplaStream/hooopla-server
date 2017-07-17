package fr.cseries.ci.video.process;

import fr.cseries.ci.mongodb.collections.MongoConversions;
import fr.cseries.ci.utils.FileUtils;
import fr.cseries.ci.video.VideoConverterQueue;
import fr.cseries.ci.video.objects.Video;
import fr.cseries.ci.video.objects.VideoPriority;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/**
 * Process de recherche de fichier non convertis.
 */
public class VideoFinderProcess extends Thread {

	@Override
	public void run() {
		super.run();

		while (true) {
			long start = System.currentTimeMillis();
			System.out.println("[~] Vérification des vidéos non convertis...");

			System.out.println("--> Nombre de thread de conversions: " + VideoConverterQueue.actualQueues.size());

			List<Video> toAdd = new ArrayList<>();

			List<File> videos = new ArrayList<>();
			FileUtils.listVideos("/var/www/cdn", videos);

			videos.forEach(file -> {
				if(file.exists()){
					if (VideoConverterQueue.actualQueues.size() == 0) {
						toAdd.add(new Video(file, VideoPriority.NORMAL, 0.0));
					} else {
						for (VideoConverterQueue videoConverterQueue : new ArrayList<>(VideoConverterQueue.actualQueues)){
							boolean counter = false;
							for(Video video : new PriorityQueue<>(videoConverterQueue.getVideosToProcess())){
								if (video.getVideoFile().getAbsolutePath().equalsIgnoreCase(file.getAbsolutePath())) counter = true;
							}
							if(!counter) toAdd.add(new Video(file, VideoPriority.NORMAL, 0.0));
						}

					}
				}
			});

			if (!toAdd.isEmpty()) {
				if (VideoConverterQueue.actualQueues.isEmpty()) {
					VideoConverterQueue vcq = new VideoConverterQueue();
					toAdd.forEach(vcq::addVideoToQueue);
				} else {
					for (Video v : toAdd) {
						VideoConverterQueue vcq = VideoConverterQueue.actualQueues.get(0);
						for (VideoConverterQueue queue : VideoConverterQueue.actualQueues) {
							if (queue.getVideosToProcess().size() < vcq.getVideosToProcess().size()) vcq = queue;
						}
						vcq.addVideoToQueue(v);
					}
				}
			}

			for (VideoConverterQueue queue : VideoConverterQueue.actualQueues) {
				if (queue.getThreadOpened() == null) {
					System.out.println("[!] Le thread de la queue " + queue.getId() + "  n'existe pas, démarrage...");
					queue.start();
				}
			}
			MongoConversions.update();

			System.out.println("--> 2 Nombre de thread de conversions: " + VideoConverterQueue.actualQueues.size());

			long end = System.currentTimeMillis();
			System.out.println("[!] Vérification des vidéos non convertis réussite : " + toAdd.size() + " vidéo(s) ajouté(s) (" + TimeUnit.MILLISECONDS.toSeconds(end - start) + "sec)");

			try {
				Thread.sleep(1000 * 20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
