package fr.cseries.ci.rabbit.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.cseries.ci.rabbit.packets.Packet;
import fr.cseries.ci.rabbit.packets.PacketRequestTorrents;
import fr.cseries.ci.torrents.TorrentsManager;

import java.io.DataInputStream;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
public class TorrentsPacketListener extends Packet {

	public TorrentsPacketListener() {
		this("");
	}

	public TorrentsPacketListener(String key) {
		super(key);
	}

	@Override
	public void readPacket(DataInputStream dataInputStream) {
		try {
			JsonObject json = new JsonParser().parse(dataInputStream.readUTF()).getAsJsonObject();
			String action = json.get("action").getAsString();
			String key = json.get("key").getAsString();
			if (key == null) throw new Exception("Aucune clé de réponse n'a été envoyé !");

			switch (action) {
				// Le packet récupère les torrents
				case "torrents.get":
					new PacketRequestTorrents(key, new Gson().toJson(TorrentsManager.getTorrents())).send();
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

