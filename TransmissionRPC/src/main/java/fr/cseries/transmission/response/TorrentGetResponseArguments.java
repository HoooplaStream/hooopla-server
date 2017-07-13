package fr.cseries.transmission.response;

import java.util.List;

public class TorrentGetResponseArguments {
	private List<Torrent> torrents;

	public List<Torrent> getTorrents() {
		return torrents;
	}

	public void setTorrents(List<Torrent> torrents) {
		this.torrents = torrents;
	}
}
