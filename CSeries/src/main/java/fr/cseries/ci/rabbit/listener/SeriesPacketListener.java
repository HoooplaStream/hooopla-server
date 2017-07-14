package fr.cseries.ci.rabbit.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.*;
import fr.cseries.ci.rabbit.packets.Packet;
import fr.cseries.ci.rabbit.packets.PacketGetEpisodes;
import fr.cseries.ci.rabbit.packets.PacketGetSeasons;
import fr.cseries.ci.rabbit.packets.PacketGetSeries;
import fr.cseries.ci.series.SeriesManager;

import java.io.DataInputStream;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
public class SeriesPacketListener extends Packet {

	public SeriesPacketListener() {
		this("");
	}

	public SeriesPacketListener(String key) {
		super(key);
	}

	@Override
	public void readPacket(DataInputStream dataInputStream) {
		try {
			JsonObject json = new JsonParser().parse(dataInputStream.readUTF()).getAsJsonObject();
			String action = json.get("action").getAsString();
			String key = json.get("key").getAsString();
			if(key == null) throw new Exception("Aucune clé de réponse n'a été envoyé !");

			switch (action){
				// Le packet récupère les séries
				case "series.get":
					new PacketGetSeries(key, SeriesManager.getSeriesAsJson()).send();
					break;
				// Le packet récupère les saisons
				case "series.saisons":
					Integer serie = json.get("serie").getAsInt();
					new PacketGetSeasons(key, SeriesManager.getSaisonsOfSerieAsJson(serie)).send();
					break;
				// Le packet récupère les épisodes
				case "series.episodes":
					Integer serieEpisode = json.get("serie").getAsInt();
					Integer saison = json.get("saison").getAsInt();
					new PacketGetEpisodes(key, SeriesManager.getEpisodesOfSeasonAsJson(serieEpisode, saison)).send();
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writePacket(ByteArrayDataOutput byteArrayDataOutput) {
	}
}

