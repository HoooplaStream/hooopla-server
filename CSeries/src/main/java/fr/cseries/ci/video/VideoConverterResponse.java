package fr.cseries.ci.video;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class VideoConverterResponse {

	/**
	 * Permet de récupére les queues de conversion en JSON
	 *
	 * @return String JSON
	 */
	public static String getQueuesAsJSON() {
		JsonArray jsonArray = new JsonArray();

		for (VideoConverterQueue videoConverterQueue : VideoConverterQueue.actualQueues){
			JsonObject jsonObject = new JsonObject();

			jsonObject.addProperty("id", videoConverterQueue.getId());

			JsonArray videos = new JsonArray();
			videoConverterQueue.getVideosToProcess().forEach(video -> {
				JsonObject videoObject = new JsonObject();

				videoObject.addProperty("name", video.getVideoFile().getName());
				videoObject.addProperty("path", video.getVideoFile().getAbsolutePath());
				videoObject.addProperty("priority", video.getPriority().getName());
				videoObject.addProperty("converting", (videoConverterQueue.getCurrentVideo().equals(video)));
				videoObject.addProperty("percent", video.getPercent());

				videos.add(videoObject);
			});

			jsonObject.add("videos", videos);
		}

		return jsonArray.getAsString();
	}

}
