package fr.cseries.ci.utils;

import com.google.common.base.Charsets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ShellUtils {

	/**
	 * Permet de lancer une commande shell
	 *
	 * @param command to execute
	 * @param read    read result
	 * @return result or empty string if read = false
	 */
	public static String executeCommand(String command, boolean read, String character) {
		return create(command, read, character);
	}

	/**
	 * Permet de lancer une commande shell
	 *
	 * @param command to execute
	 * @param read    read result
	 * @return result or empty string if read = false
	 */
	public static String executeCommand(String command, boolean read) {
		return create(command, read, "\n");
	}

	private static String create(String command, boolean read, String character) {
		File dir = new File("tmp");
		if (!dir.exists()) dir.mkdir();
		File f = new File("tmp/WAIT@script-" + new Random().nextInt(Integer.MAX_VALUE) + ".sh");
		StringBuilder output = new StringBuilder();
		try {
			f.createNewFile();
			f.setReadable(true, false);
			f.setWritable(true, false);
			f.setExecutable(true, false);
			Files.write(f.toPath(), Arrays.asList("#!/bin/bash", command));
			ProcessBuilder pb = new ProcessBuilder("/bin/sh", "/home/CSeries/tmp/" + f.getName());
			Process p = pb.start();

			if (read) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), Charsets.UTF_8));

				String line;
				while ((line = reader.readLine()) != null) {
					output.append(line).append(character);
				}
			}
			f.renameTo(new File("tmp/" + f.getName().replace("WAIT", "DONE")));
			return output.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ".";
	}

	/**
	 * Permet de lancer une commande shell en async sans lecture du resultat
	 *
	 * @param command to execute
	 */
	public static void executeCommand(String command) {
		new Thread(() -> executeCommand(command, false)).start();
	}

}
