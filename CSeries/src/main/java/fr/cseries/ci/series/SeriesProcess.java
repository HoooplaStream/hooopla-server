package fr.cseries.ci.series;

import fr.cseries.ci.Config;
import fr.cseries.ci.mongodb.collections.MongoSeries;
import fr.cseries.ci.series.objects.Episode;
import fr.cseries.ci.series.objects.Season;
import fr.cseries.ci.series.objects.Serie;
import fr.cseries.ci.utils.FileUtils;
import fr.cseries.ci.video.VideoConverterQueue;
import fr.cseries.ci.video.objects.Video;
import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeries;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SeriesProcess extends Thread {

	@Override
	public void run() {
		super.run();

		while (true) {
			System.out.println("[!] Mise à jour de la liste des séries...");
			long start = System.currentTimeMillis();
			SeriesManager.getSeries().clear();

			List<File> folders = new ArrayList<>();

			File folder = new File("/var/www/cdn/series");
			File[] content = folder.listFiles();
			Arrays.sort(content);
			for (File files : content) {
				if (files.isDirectory()) folders.add(files);
			}

			for (File f : folders) {
				try {
					TvSeries tv = SeriesAPI.searchForFirstSerie(f.getName());
					Serie serie = new Serie(tv.getId(), tv.getName(), tv.getOverview(), tv.getBackdropPath(), tv.getPosterPath(), tv.getVoteAverage(), tv.getVoteCount(), new ArrayList<>());

					File[] seasonsFiles = f.listFiles();
					Arrays.sort(seasonsFiles);
					List<Season> seasons = new ArrayList<>();
					for (File inside : seasonsFiles) {
						if (inside.isDirectory()) {
							if (inside.getName().contains("Saison_")){
								Season season = new Season(Integer.parseInt(inside.getName().replace("Saison_", "")), Integer.parseInt(inside.getName().replace("Saison_", "")), "");

								List<Episode> episodes = new ArrayList<>();
								int counter = 1;
								File[] episodesFiles = inside.listFiles();
								Arrays.sort(episodesFiles);
								for (File episode : episodesFiles) {
									if (FileUtils.getExtension(episode).contains("mp4")) {
										Boolean verify = false;
										for(VideoConverterQueue videoConverterQueue : new ArrayList<>(VideoConverterQueue.actualQueues)){
											for(Video video : videoConverterQueue.getVideosToProcess()){
												if(video.getVideoFile().getAbsolutePath().equalsIgnoreCase(episode.getAbsolutePath())){
													verify = true;
												}
											}
										}

										if(!verify){
											TvEpisode tvEpisode = SeriesAPI.getEpisode(serie, season, counter);
											episodes.add(new Episode(tvEpisode.getEpisodeNumber(), tvEpisode.getName(), tvEpisode.getOverview(), Config.CDN + "/" + f.getName() + "/" + inside.getName() + "/" + episode.getName(), tvEpisode.getUserRating(), false));
										}
									}
									counter++;
								}
								season.setEpisodeList(episodes);

								seasons.add(season);
								serie.getSeasons().add(season);
							}
						}
					}

					SeriesManager.addSerie(serie);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			long end = System.currentTimeMillis();
			System.out.println("[!] La mise à jour des séries a été faîte (" + TimeUnit.MILLISECONDS.toSeconds(end - start) + "sec)");

			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
