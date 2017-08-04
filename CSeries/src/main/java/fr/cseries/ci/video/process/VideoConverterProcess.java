package fr.cseries.ci.video.process;

import fr.cseries.ci.mongodb.collections.MongoConversions;
import fr.cseries.ci.video.VideoConverter;
import fr.cseries.ci.video.VideoConverterQueue;
import fr.cseries.ci.video.objects.Video;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Process de conversion d'une queue.
 */
public class VideoConverterProcess extends Thread {

	@Getter
	@Setter
	@NonNull
	private VideoConverterQueue queue;
	private Boolean finished = false;

	public VideoConverterProcess(VideoConverterQueue queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		super.run();

		System.out.println("[-->] Démarrage du thread de conversion " + queue.getId());

		while (true) {
			if (!queue.getVideosToProcess().isEmpty()) {
				System.out.println("[" + queue.getId() + "] La file d'attente n'est pas vide, démarrage !");
				this.finished = false;
				Video video = queue.getVideosToProcess().poll();
				queue.setCurrentVideo(video);
				queue.setCounter(queue.getCounter() + 1);

				System.out.println("[" + queue.getId() + "] Vérification " + video.getVideoFile().getName());

				if (video.getVideoFile().exists()) {
					System.out.println("[" + queue.getId() + "] Démarrage " + video.getVideoFile().getName());
					VideoConverter videoConverter = new VideoConverter(video.getVideoFile()) {
						@Override
						public void onProgress(double percent) {
							for(Video v : queue.getVideosToProcess()){
								if(v.getVideoFile().getAbsolutePath().equalsIgnoreCase(video.getVideoFile().getAbsolutePath())){
									v.setPercent(percent);
									MongoConversions.update();
								}
							}
						}

						@Override
						public void onFinish() {

						}
					};
					videoConverter.start();
					video.getVideoFile().delete();
				}
			}else System.out.println("[" + queue.getId() + "] La file d'attente est vide !");
		}
	}

}
