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

	@Getter @Setter @NonNull
	private VideoConverterQueue queue;
	private Boolean finished = false;

	public VideoConverterProcess(VideoConverterQueue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		super.run();

		while (queue.getVideosToProcess().size() > 0) {
			this.finished = false;
			Video video = queue.getVideosToProcess().poll();

			if (video.getVideoFile().exists()) {
				VideoConverter videoConverter = new VideoConverter(video.getVideoFile()) {
					@Override
					public void onProgress(Double percent) {
						// todo envoi de packets permettant d'ajouter au panel les conversions
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
				while (!this.finished) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
