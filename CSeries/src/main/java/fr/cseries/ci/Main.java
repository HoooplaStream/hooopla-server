package fr.cseries.ci;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("[~] Starting the CSeries Server...");

		System.out.println("[~] Starting the server...");
		Bootstrap.boot();
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
