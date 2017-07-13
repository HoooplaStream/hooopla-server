package fr.cseries.ci.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Gestion du rabbit
 */
public class RabbitManager {

	private static Channel channel;

	/**
	 * A executer pour mettre en place Rabbit
	 */
	public static void init() {
		System.out.println("[*] Connecting to Rabbit server...");
		try {
			ConnectionFactory connectionFactory = new ConnectionFactory();

			connectionFactory.setHost("X.X.X.X");
			connectionFactory.setUsername("A FAIRE");
			connectionFactory.setPassword("A FAIRE");

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

