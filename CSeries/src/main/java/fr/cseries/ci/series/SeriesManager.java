package fr.cseries.ci.series;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.cseries.ci.series.objects.Serie;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SeriesManager {

	@Getter
	@Setter
	public static List<Serie> series = new ArrayList<>();

	public static void addSerie(Serie serie) {
		series.add(serie);
	}

	public static String getSeriesAsJson() {
		JsonArray jsonArray = new JsonArray();
		series.forEach(serie -> {
			JsonObject jsonObject = new JsonObject();

			jsonObject.addProperty("id", serie.getId());
			jsonObject.addProperty("name", serie.getName());
			jsonObject.addProperty("description", serie.getDescription());
			jsonObject.addProperty("background", serie.getBackground());
			jsonObject.addProperty("cover", serie.getCover());
			jsonObject.addProperty("rating", serie.getStar());

			jsonArray.add(jsonObject);
		});

		return jsonArray.getAsString();
	}

}
