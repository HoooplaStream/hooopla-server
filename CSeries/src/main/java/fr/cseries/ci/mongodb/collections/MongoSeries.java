package fr.cseries.ci.mongodb.collections;

import com.mongodb.client.MongoCollection;
import fr.cseries.ci.mongodb.MongoDB;
import fr.cseries.ci.series.objects.Serie;
import org.bson.Document;

import java.util.HashMap;

public class MongoSeries {

	private static MongoCollection<Document> dbCollection;

	public static void add(Serie serie) {
		String collectionName = "series";
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

			long d = dbCollection.count(new Document("id", serie.getId()));
			if (d == 0) {
				dbCollection.insertOne(new Document(document));
			} else {
				dbCollection.updateOne(new Document("id", serie.getId()), new Document("$set", document));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// todo remove series whove been remloved
}
