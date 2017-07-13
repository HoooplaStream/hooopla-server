package fr.cseries.ci.torrents;

import fr.cseries.ci.Config;
import fr.cseries.transmission.TransmissionClient;
import fr.cseries.transmission.exception.AuthException;
import fr.cseries.transmission.exception.NetworkException;
import fr.cseries.transmission.response.Torrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Permet de gérer les torrents sur la machine même.
 *
 * @see Torrent
 */
public class TorrentsManager {

	/**
	 * Récupère le client Torrent local.
	 *
	 * @return TransmissionClient
	 */
	public static TransmissionClient getClient() {
		return new TransmissionClient(Config.RPC_USER, Config.RPC_PASSWORD, Config.RPC_IP);
	}

	// Get

	/**
	 * Permet de récupérer la liste des torrent.
	 *
	 * @return List<Torrent>
	 * @see Torrent
	 */
	public static List<Torrent> getTorrents() {
		try {
			return getClient().getAll();
		} catch (AuthException | NetworkException e) {
			e.printStackTrace();
		}
		return new ArrayList<Torrent>();
	}

	/**
	 * Permet de récupérer certains torrents
	 *
	 * @param torrent List<Integer> Liste d'integer représentant l'id des torrents.
	 *
	 * @return List<Torrent>
	 * @see Torrent
	 */
	public static List<Torrent> getTorrent(Integer... torrent){
		try {
			return getClient().get(Arrays.asList(torrent));
		} catch (AuthException | NetworkException e) {
			e.printStackTrace();
		}
		return new ArrayList<Torrent>();
	}


}
