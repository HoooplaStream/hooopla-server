package fr.cseries.ci.mongodb.collections;

import com.mongodb.client.MongoCollection;
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
				seasonDocument.put("episodes", new ArrayList<Document>());

				saisons.add(new Document(seasonDocument));
			});
			document.put("saisons", saisons);

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

	public static void addSaison(Serie serie, Season season) {
		if (dbCollection == null) dbCollection = MongoDB.db.getDatabase().getCollection(collectionName);
		try {
			long d = dbCollection.count(new Document("id", serie.getId()));
			if (d == 0) add(serie);

			HashMap<String, Object> seasonDocument = new HashMap<>();
			seasonDocument.put("id", serie.getId());
			seasonDocument.put("number", serie.getName());
			seasonDocument.put("poster", serie.getDescription());

			List<Document> findDocument = dbCollection.find(new Document("id", serie.getId())).into(new ArrayList<Document>());
			boolean contain = false;
			for (Document doc : findDocument){
				if(doc.containsKey("id")){
					if (doc.get("id").equals(season.getId())){
						contain = true;
					}
				}
			}
			findDocument.add(new Document(seasonDocument));

			HashMap<String, Object> document = new HashMap<>();
			document.put("seasons", findDocument);

			dbCollection.updateOne(new Document("id", serie.getId()), new Document("$set", document));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// todo remove series whove been remloved
}
