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

	/**
	 * Permet de récupérer en JSON les séries disponibles.
	 *
	 * @return JSON
	 */
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

	/**
	 * Permet de récuperer les saisons d'une série en JSON
	 *
	 * @param serieID ID de la série.
	 *
	 * @return JSON
	 */
	public static String getSaisonsOfSerieAsJson(Integer serieID) {
		JsonArray jsonArray = new JsonArray();
		series.forEach(serie -> {
			if(serie.getId().equals(serieID)){
				serie.getSeasons().forEach(season -> {
					JsonObject jsonObject = new JsonObject();

					jsonObject.addProperty("id", season.getId());
					jsonObject.addProperty("name", "Saison " + season.getNumber());
					jsonObject.addProperty("date", season.getDate());

					jsonArray.add(jsonObject);
				});
			}
		});

		return jsonArray.getAsString();
	}

	/**
	 * Permet de récuperer les épisodes d'une série en JSON
	 *
	 * @param serieID ID de la série.
	 * @param saisonNumber Numéro de la saison.
	 *
	 * @return JSON
	 */
	public static String getEpisodesOfSeasonAsJson(Integer serieID, Integer saisonNumber) {
		JsonArray jsonArray = new JsonArray();
		series.forEach(serie -> {
			if(serie.getId().equals(serieID)){
				serie.getSeasons().forEach(season -> {
					if(season.getNumber().equals(saisonNumber)){
						season.getEpisodeList().forEach(episode -> {
							JsonObject jsonObject = new JsonObject();

							jsonObject.addProperty("number", episode.getNumber());
							jsonObject.addProperty("name", episode.getName());
							jsonObject.addProperty("description", episode.getDescription());
							jsonObject.addProperty("rating", episode.getRating());
							jsonObject.addProperty("url", episode.getUrl());

							jsonArray.add(jsonObject);
						});
					}
				});
			}
		});

		return jsonArray.getAsString();
	}

}
