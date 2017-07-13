package fr.cseries.ci.series;

import fr.cseries.ci.Config;
import fr.cseries.ci.series.objects.Episode;
import fr.cseries.ci.series.objects.Season;
import fr.cseries.ci.series.objects.Serie;
import fr.cseries.ci.utils.FileUtils;
import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeries;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SeriesProcess extends Thread {

	@Override
	public void run() {
		super.run();

		while (true) {
			System.out.println("[!] Mise à jour de la liste des séries...");
			long start = System.currentTimeMillis();

			List<File> folders = new ArrayList<>();

			File folder = new File("/var/www/cdn");
			File[] content = folder.listFiles();
			Arrays.sort(content);
			for (File files : content) {
				if (files.isDirectory()) folders.add(files);
			}

			for (File f : folders) {
				try {
					TvSeries tv = SeriesAPI.searchForFirstSerie(f.getName());
					Serie serie = new Serie(tv.getId(), tv.getName(), tv.getOverview(), tv.getBackdropPath(), tv.getPosterPath(), tv.getVoteAverage(), new ArrayList<>());

					List<Season> seasons = new ArrayList<>();
					File[] seasonsFiles = folder.listFiles();
					Arrays.sort(seasonsFiles);
					for (File inside : seasonsFiles) {
						if (inside.getName().contains("Saison_")) {
							tv.getSeasons().forEach(tvSeason -> {
								if (Objects.equals(inside.getName().replace("Saison_", ""), "" + tvSeason.getSeasonNumber())) {
									Season season = new Season(tvSeason.getId(), tvSeason.getSeasonNumber(), tvSeason.getAirDate());

									List<Episode> episodes = new ArrayList<>();
									int counter = 1;
									File[] episodesFiles = inside.listFiles();
									Arrays.sort(episodesFiles);
									for(File episode : episodesFiles){
										if(FileUtils.getExtension(episode).contains(".mp4")){
											TvEpisode tvEpisode = SeriesAPI.getEpisode(serie, season, counter);
											episodes.add(new Episode(tvEpisode.getEpisodeNumber(), tvEpisode.getName(), tvEpisode.getOverview(), Config.CDN + "/" + f.getName() + "/" + inside.getName() + "/" + episode.getName(), tvEpisode.getUserRating()));
										}
										counter++;
									}
									season.setEpisodeList(episodes);

									seasons.add(season);
								}
							});
						}
					}

					SeriesManager.addSerie(serie);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			long end = System.currentTimeMillis();
			System.out.println("[!] La mise à jour des séries a été faîte (en " + TimeUnit.MILLISECONDS.toSeconds(start - end) + "sec) !");

			try {
				Thread.sleep(1000 * 60 * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
