package fr.cseries.ci.video.process;

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

				System.out.println("[" + queue.getId() + "] Démarrage " + video.getVideoFile().getName());

				if (video.getVideoFile().exists()) {
					VideoConverter videoConverter = new VideoConverter(video.getVideoFile()) {
						@Override
						public void onProgress(Double percent) {
							video.setPercent(percent);
						}

						@Override
						public void onFinish() {
							finished = true;
							try {
								wait(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							getFile().delete();
						}
					};
					videoConverter.start();
					while(this.finished){
						System.out.println("[" + queue.getId() + "] Conversion en cours de " + video.getVideoFile().getName() + " : " + video.getPercent() + "%");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}else System.out.println("[" + queue.getId() + "] La file d'attente est vide !");
		}
	}

}
