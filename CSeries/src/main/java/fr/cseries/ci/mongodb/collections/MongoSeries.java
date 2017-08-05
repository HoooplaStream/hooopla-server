package fr.cseries.ci.mongodb.collections;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import fr.cseries.ci.mongodb.MongoDB;
import fr.cseries.ci.series.objects.Season;
import fr.cseries.ci.series.objects.Serie;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MongoSeries {

	private static String collectionName = "series";
	private static MongoCollection<Document> dbCollection;

	public static void add(Serie serie) {
		if (dbCollection == null) dbCollection = MongoDB.db.getDatabase().getCollection(collectionName);
		try {
			HashMap<String, Object> document = new HashMap<>();
			document.put("id", serie.getId());
			document.put("name", serie.getName());
			document.put("description", serie.getDescription());
			document.put("cover", serie.getCover());
			document.put("background", serie.getBackground());
			document.put("rating", serie.getStar());
			document.put("voters", serie.getVoters());


			List<Document> saisons = new ArrayList<>();
			serie.getSeasons().forEach(season -> {
				HashMap<String, Object> seasonDocument = new HashMap<>();
				seasonDocument.put("id", season.getId());
				seasonDocument.put("number", season.getNumber());
				seasonDocument.put("ready", season.getReady());

				List<Document> episodes = new ArrayList<>();
				season.getEpisodeList().forEach(episode -> {
					HashMap<String, Object> episodeDocument = new HashMap<>();
					episodeDocument.put("id", episode.getNumber());
					episodeDocument.put("name", episode.getName());
					episodeDocument.put("description", episode.getDescription());
					episodeDocument.put("url", episode.getUrl());
					episodeDocument.put("rating", episode.getRating());
					episodeDocument.put("ready", episode.getReady());

					episodes.add(new Document(episodeDocument));
				});
				seasonDocument.put("episodes", episodes);

				saisons.add(new Document(seasonDocument));
			});
			document.put("saisons", saisons);

			dbCollection.updateOne(new Document("id", serie.getId()), new Document("$set", document), new UpdateOptions().upsert(true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
