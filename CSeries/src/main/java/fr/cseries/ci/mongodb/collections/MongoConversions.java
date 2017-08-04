package fr.cseries.ci.mongodb.collections;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import fr.cseries.ci.mongodb.MongoDB;
import fr.cseries.ci.video.VideoConverter;
import fr.cseries.ci.video.VideoConverterQueue;
import org.bson.Document;

import java.lang.reflect.Array;
import java.util.*;

public class MongoConversions {

	private static String collectionName = "conversions";
	private static MongoCollection<Document> dbCollection;

	public static void update() {
		if (dbCollection == null) dbCollection = MongoDB.db.getDatabase().getCollection(collectionName);
		try {
			if(VideoConverterQueue.actualQueues.size() > 0){
				for (VideoConverterQueue videoConverterQueue : new ArrayList<>(VideoConverterQueue.actualQueues)){
					HashMap<String, Object> fields = new HashMap<>();
					fields.put("id", videoConverterQueue.getId());

					List<Document> videos = new ArrayList<>();
					if(videoConverterQueue.getVideosToProcess().size() > 0){
						new PriorityQueue<>(videoConverterQueue.getVideosToProcess()).forEach(video -> {
							HashMap<String, Object> seasonDocument = new HashMap<>();
							seasonDocument.put("id", video.getVideoFile().getName());
							seasonDocument.put("path", video.getVideoFile().getAbsolutePath());
							seasonDocument.put("percent", video.getPercent());
							seasonDocument.put("priority", video.getPriority().getName());

							videos.add(new Document(seasonDocument));
						});
						fields.put("videos", videos);
					} else fields.put("videos", new ArrayList<>());

					dbCollection.updateOne(new Document("id", videoConverterQueue.getId()), new Document("$set", fields), new UpdateOptions().upsert(true));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void drop(){
		if (dbCollection == null) dbCollection = MongoDB.db.getDatabase().getCollection(collectionName);
		dbCollection.drop();
	}
}
