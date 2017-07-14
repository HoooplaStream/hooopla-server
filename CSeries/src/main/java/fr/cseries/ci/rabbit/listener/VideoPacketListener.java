package fr.cseries.ci.rabbit.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.cseries.ci.rabbit.packets.Packet;
import fr.cseries.ci.rabbit.packets.PacketGetQueues;
import fr.cseries.ci.rabbit.packets.PacketGetSeries;
import fr.cseries.ci.series.SeriesManager;
import fr.cseries.ci.video.VideoConverterResponse;

import java.io.DataInputStream;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
public class VideoPacketListener extends Packet {

	public VideoPacketListener() {
		this("");
	}

	public VideoPacketListener(String key) {
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
				case "video.queues":
					new PacketGetQueues(key, VideoConverterResponse.getQueuesAsJSON()).send();
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

