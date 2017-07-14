package fr.cseries.ci.video;

import fr.cseries.ci.video.objects.Video;
import fr.cseries.ci.video.process.VideoConverterProcess;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.*;

public class VideoConverterQueue {

	public static List<VideoConverterQueue> actualQueues = new ArrayList<>();

	@Getter @Setter @NonNull
	public String id;
	@Getter @Setter
	private Queue<Video> videosToProcess = new PriorityQueue<>(Comparator.comparingInt(o -> o.getPriority().getPriority()));
	@Getter @Setter
	public Video currentVideo;

	private VideoConverterProcess threadOpened;

	/**
	 * Permet de créer une queue de conversion.
	 */
	public VideoConverterQueue() {
		actualQueues.add(this);
		this.id = "queue-" + System.currentTimeMillis();
	}

	/**
	 * Permet de démarrer les conversions selon la queue.
	 */
	public void start() {
		this.threadOpened = new VideoConverterProcess(this);
		this.threadOpened.run();
	}

	/**
	 * Permet de démarrer les conversions selon la queue.
	 */
	public void resume() throws Exception {
		if (!threadOpened.isInterrupted()) throw new Exception("Le thread actuel n'est pas en pause !");
		this.threadOpened.join();
	}

	/**
	 * Permet de démarrer les conversions selon la queue.
	 */
	public void pause() throws Exception {
		if (threadOpened.isInterrupted()) throw new Exception("Le thread actuel est déjà en pause !");
		this.threadOpened.interrupt();
	}

	/**
	 * Ajout d'une vidéo dans la queue.
	 *
	 * @param video Video
	 * @see Video
	 */
	public void addVideoToQueue(Video video) {
		this.videosToProcess.add(video);
	}

}
