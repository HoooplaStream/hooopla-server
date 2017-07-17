package fr.cseries.ci;

import fr.cseries.ci.mongodb.collections.MongoConversions;
import fr.cseries.ci.series.SeriesProcess;
import fr.cseries.ci.video.process.VideoFinderProcess;

import java.io.File;

public class Bootstrap {

	/**
	 * Dossier qui necessite d'être crée à l'installation.
	 */
	private static File[] folderToBeCreated = {
			new File("/var/www"), new File("/var/www/cdn"),
	};

	/**
	 * Méthode executé au démarrage de l'app.
	 */
	public static void boot() {
		// {Vérification de la structure du serveur]
		System.out.println("[~] Verifying the folder structure...");
		for (File f : folderToBeCreated) {
			if (!f.exists()) {
				if (!f.mkdir()) try {
					throw new Exception("Le fichier " + f.getName() + " n'a pas pu être crée !");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("[+] Folder structure verified !");

		// [MongoDB]
		System.out.println("[~] Loading MongoDB...");
		MongoConversions.drop();
		System.out.println("[+] MongoDB loaded !");

		/*// [Rabbit]
		System.out.println("[~] Loading RabbitMQ...");
		// Listener
		PacketListener.packets.add(SeriesPacketListener.class);
		PacketListener.packets.add(TorrentsPacketListener.class);
		PacketListener.packets.add(VideoPacketListener.class);
		// Send
		PacketListener.packets.add(PacketRequestTorrents.class);
		PacketListener.packets.add(PacketGetSeasons.class);
		PacketListener.packets.add(PacketGetSeries.class);
		PacketListener.packets.add(PacketGetEpisodes.class);
		PacketListener.packets.add(PacketGetQueues.class);
		// Init
		RabbitManager.init();
		System.out.println("[+] Rabbit loaded !");*/

		// [Démarrage des process]
		new SeriesProcess().start();
		new VideoFinderProcess().start();
	}

}
