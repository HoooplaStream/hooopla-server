package fr.cseries.ci.rabbit.packets;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.cseries.ci.rabbit.PacketListener;
import fr.cseries.ci.rabbit.RabbitManager;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class Packet {

	private String              key;
	private ByteArrayDataOutput byteArrayDataOutput;

	public Packet(String key) {
		this.key = key;
		this.byteArrayDataOutput = ByteStreams.newDataOutput();
	}

	public void writeDef() {
		this.byteArrayDataOutput.writeUTF(getClass().getName());
		writePacket(this.byteArrayDataOutput);
	}

	public synchronized void send() {
		writeDef();
		try {
			RabbitManager.getChannel().basicPublish("main", this.key, null, this.byteArrayDataOutput.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
			RabbitManager.init();
			new PacketListener();
		}
	}

	// Abstrait

	public abstract void readPacket(DataInputStream data);

	public abstract void writePacket(ByteArrayDataOutput data);
}

