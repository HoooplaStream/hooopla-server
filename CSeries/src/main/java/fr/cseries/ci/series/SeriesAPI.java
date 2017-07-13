package fr.cseries.ci.series;

import fr.cseries.ci.Config;
import fr.cseries.ci.series.objects.Season;
import fr.cseries.ci.series.objects.Serie;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeries;

import java.util.List;

public class SeriesAPI {

	private static TmdbApi getAPI(){
		return new TmdbApi(Config.TMDB_API);
	}

	public static TvSeries searchForFirstSerie(String serie) throws Exception {
		List<TvSeries> series = getAPI().getSearch().searchTv(serie, "fr-FR", 1).getResults();
		if(series.size() == 0) throw new Exception("La série n'a pas été trouvé !");
		return series.get(0);
	}

	public static TvEpisode getEpisode(Serie serie, Season season, Integer episodeNumber){
		return getAPI().getTvEpisodes().getEpisode(serie.getId(), season.getNumber(), episodeNumber, "fr-FR");
	}

}
