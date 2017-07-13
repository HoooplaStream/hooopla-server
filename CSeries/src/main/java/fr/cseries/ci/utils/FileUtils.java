package fr.cseries.ci.utils;

import java.io.File;

public class FileUtils {

	public static String getExtension(File f) {
		String extension = "";

		int i = f.getAbsolutePath().lastIndexOf('.');
		if (i > 0) extension = f.getAbsolutePath().substring(i + 1);

		return extension;
	}

}
