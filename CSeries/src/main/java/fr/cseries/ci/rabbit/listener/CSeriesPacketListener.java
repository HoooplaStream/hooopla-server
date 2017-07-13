package fr.cseries.ci.rabbit.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.*;
import fr.cseries.ci.rabbit.packets.Packet;
import fr.cseries.ci.rabbit.packets.PacketGetSeries;
import fr.cseries.ci.rabbit.packets.PacketRequestTorrents;
import fr.cseries.ci.series.SeriesManager;
import fr.cseries.ci.torrents.TorrentsManager;

import java.io.DataInputStream;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
public class CSeriesPacketListener extends Packet {

	public CSeriesPacketListener() {
		this("");
	}

	public CSeriesPacketListener(String key) {
		super(key);
	}

	@Override
	public void readPacket(DataInputStream dataInputStream) {
		try {
			JsonObject json = new JsonParser().parse(dataInputStream.readUTF()).getAsJsonObject();
			String action = json.get("action").getAsString();
			String key = json.get("key").getAsString();
			if(key == null) throw new Exception("Aucune clé de réponse n'a été renvoyé !");

			switch (action){
				// Le packet récupère les séries
				case "series.get":
					new PacketGetSeries(key, new Gson().toJson(SeriesManager.getSeriesAsJson())).send();
					break;
				// Le packet récupère les torrents
				case "torrents.get":
					new PacketRequestTorrents(key, new Gson().toJson(TorrentsManager.getTorrents())).send();
					break;

			}

			if (action.equalsIgnoreCase("torrents.get")) {
				TorrentsManager.getTorrents();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writePacket(ByteArrayDataOutput byteArrayDataOutput) {
	}
}

