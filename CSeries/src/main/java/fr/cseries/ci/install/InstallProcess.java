package fr.cseries.ci.install;

import java.io.File;

public class InstallProcess {

	/**
	 * Dossier qui necessite d'être crée à l'installation.
	 */
	private static File[] folderToBeCreated = {
		new File("/var/www"), new File("/var/www/cdn"),
	};

	/**
	 * Méthode executé au démarrage de l'app.
	 */
	public static void boot(){
		for (File f : folderToBeCreated) {
			if(!f.exists()) f.mkdir();
		}
	}

}
