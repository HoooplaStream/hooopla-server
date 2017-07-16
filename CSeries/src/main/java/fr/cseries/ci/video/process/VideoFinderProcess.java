package fr.cseries.ci.video.process;

import fr.cseries.ci.utils.FileUtils;
import fr.cseries.ci.video.VideoConverterQueue;
import fr.cseries.ci.video.objects.Video;
import fr.cseries.ci.video.objects.VideoPriority;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

			File f = new File("/var/www/cdn");
			if (!f.exists() || !f.isDirectory()) try {
				throw new Exception("Le dossier cdn dans /var/www/cdn n'existe pas !");
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<Video> toAdd = new ArrayList<>();

			for (File serieFolder : f.listFiles(File::isDirectory)) {
				File[] seasonsFolder = serieFolder.listFiles(File::isDirectory);
				if (seasonsFolder.length > 0) {
					for (File seasonFolder : seasonsFolder) {
						File[] episodesFolder = seasonFolder.listFiles(File::isFile);
						if (episodesFolder.length > 0) {
							for (File episodeFile : episodesFolder) {
								if (!FileUtils.getExtension(episodeFile).contains(".mp4")) {
									VideoConverterQueue.actualQueues.forEach(videoConverterQueue -> videoConverterQueue.getVideosToProcess().forEach(video -> {
										if (video.getVideoFile().getAbsolutePath().equalsIgnoreCase(episodeFile.getAbsolutePath()))
											toAdd.add(new Video(episodeFile, VideoPriority.NORMAL, 0.0));
									}));
								}
							}
						}
					}
				}
			}

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

			long end = System.currentTimeMillis();
			System.out.println("[!] Vérification des vidéos non convertis réussite (" + TimeUnit.MILLISECONDS.toSeconds(start - end) + "sec)");

			try {
				Thread.sleep(1000 * 60 * 5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
