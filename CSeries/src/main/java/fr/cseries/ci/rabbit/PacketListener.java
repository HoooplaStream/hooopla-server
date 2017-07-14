package fr.cseries.ci.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.cseries.ci.rabbit.packets.Packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketListener {

	public static List<Class> packets   = new ArrayList<>();

	public PacketListener() {
		System.out.println("[*] Loading Listener...");
		try {
			String queueName = RabbitManager.getChannel().queueDeclare().getQueue();

			RabbitManager.getChannel().queueBind(queueName, "main", "CSERIES_CI");
			Consumer consumer = new DefaultConsumer(RabbitManager.getChannel()) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
					DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(body));
					String packetName = dataInputStream.readUTF();
					boolean b = false;
					System.out.println("[X] Packet receive: " + packetName);
					for (Class c : packets) {
						if (c.getName().equalsIgnoreCase(packetName.trim())) {
							b = true;
							new Thread(() -> {
								try {
									Packet packet = (Packet) c.newInstance();
									packet.readPacket(dataInputStream);
								} catch (InstantiationException | IllegalAccessException e) {
									e.printStackTrace();
								}
							}).start();
							break;
						}
					}

					if (!b) {
						System.out.println("[X] Unknown packet receive: " + packetName);
					}
				}
			};
			RabbitManager.getChannel().basicConsume(queueName, true, consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[*] Listener loaded!");
	}
}

