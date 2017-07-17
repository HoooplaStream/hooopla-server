package fr.cseries.ci.utils;

import java.io.File;
import java.util.List;

public class FileUtils {

	public static String[] movieExtension = {".avi", ".webm", ".mov", ".mkv", ".flv"};

	public static String getExtension(File f) {
		return getExtension(f.getName());
	}

	public static String getExtension(String f) {
		String extension = "";

		int i = f.lastIndexOf('.');
		if (i > 0) extension = f.substring(i + 1);

		return extension;
	}

	public static void listVideos(String directoryName, List<File> files) {
		File directory = new File(directoryName);

		File[] fList = directory.listFiles();
		if (fList != null) {
			for (File file : fList) {
				if (file.isFile()) {
					for (String extensions : movieExtension) {
						if (file.getName().endsWith(extensions)) files.add(file);
					}
				} else if (file.isDirectory()) {
					listVideos(file.getAbsolutePath(), files);
				}
			}
		}
	}

}
