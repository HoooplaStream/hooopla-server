package fr.cseries.ci;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {

	public static void main(String[] args) {
		System.out.println("[~] Starting the CSeries Server...");

		System.out.println("[~] Starting the server...");
		Bootstrap.boot();
		System.out.println("[+] Server started !");

		/*try {
			PrintStream out = new PrintStream(new FileOutputStream("log.txt"));
			System.setOut(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
	}


}
