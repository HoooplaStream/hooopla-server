package fr.cseries.ci;

import fr.cseries.ci.install.InstallProcess;
import fr.cseries.ci.rabbit.PacketListener;
import fr.cseries.ci.rabbit.RabbitManager;
import fr.cseries.ci.rabbit.packets.PacketRequestTorrents;
import fr.cseries.ci.series.SeriesProcess;
import fr.cseries.ci.video.process.VideoFinderProcess;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("[~] Starting the CSeries Server...");

		System.out.println("[~] Loading RabbitMQ...");
		PacketListener.packets.add(PacketRequestTorrents.class);
		RabbitManager.init();
		System.out.println("[+] Rabbit loaded !");

		System.out.println("[~] Starting the server...");
		InstallProcess.boot();
		//new SeriesProcess().run();
		//new VideoFinderProcess().run();
		System.out.println("[+] Server started !");

		Scanner cl = new Scanner(System.in);
		while (true) {
			String rep = cl.nextLine();
			if (rep.equalsIgnoreCase("shutdown") || rep.equalsIgnoreCase("stop")) {
				break;
			}
		}
		cl.close();
	}


}
