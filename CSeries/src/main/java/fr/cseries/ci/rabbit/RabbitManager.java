package fr.cseries.ci.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import fr.cseries.ci.Config;

/**
 * Gestion du rabbit
 */
public class RabbitManager {

	private static Channel channel;

	public static void init() {
		System.out.println("[*] Connecting to Rabbit server...");
		try {
			ConnectionFactory connectionFactory = new ConnectionFactory();

			connectionFactory.setHost(Config.RABBIT_IP);
			connectionFactory.setUsername(Config.RABBIT_USER);
			connectionFactory.setPassword(Config.RABBIT_PASSWORD);

			Connection connection = connectionFactory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare("main", "direct");

			System.out.println("[*] Connected !");
			new PacketListener();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("/!\\ ERROR connection...");
		}
	}

	public static Channel getChannel() {
		return channel;
	}
}

