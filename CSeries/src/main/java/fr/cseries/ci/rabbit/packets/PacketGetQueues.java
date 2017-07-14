package fr.cseries.ci.rabbit.packets;

import com.google.common.io.ByteArrayDataOutput;

import java.io.DataInputStream;
import java.io.IOException;

public class PacketGetQueues extends Packet {

	private String json;

	public PacketGetQueues() {
		this("", "");
	}

	public PacketGetQueues(String key, String json) {
		super(key);
		this.json = json;
	}

	@Override
	public void readPacket(DataInputStream data) {
		try {
			json = data.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writePacket(ByteArrayDataOutput data) {
		data.writeUTF(json);
	}

	public String getJson() {
		return json;
	}

}
