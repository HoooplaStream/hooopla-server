package fr.cseries.transmission.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionStatsResponseArguments {
	private long activeTorrentCount;
	private long downloadSpeed;
	private long pausedTorrentCount;
	private long torrentCount;
	private long uploadSpeed;

	@JsonProperty("cumulative-stats")
	private SessionStats cumnlativeStats;

	@JsonProperty("current-stats")
	private SessionStats currentStats;

	public long getActiveTorrentCount() {
		return activeTorrentCount;
	}

	public void setActiveTorrentCount(long activeTorrentCount) {
		this.activeTorrentCount = activeTorrentCount;
	}

	public long getDownloadSpeed() {
		return downloadSpeed;
	}

	public void setDownloadSpeed(long downloadSpeed) {
		this.downloadSpeed = downloadSpeed;
	}

	public long getPausedTorrentCount() {
		return pausedTorrentCount;
	}

	public void setPausedTorrentCount(long pausedTorrentCount) {
		this.pausedTorrentCount = pausedTorrentCount;
	}

	public long getTorrentCount() {
		return torrentCount;
	}

	public void setTorrentCount(long torrentCount) {
		this.torrentCount = torrentCount;
	}

	public long getUploadSpeed() {
		return uploadSpeed;
	}

	public void setUploadSpeed(long uploadSpeed) {
		this.uploadSpeed = uploadSpeed;
	}

	public SessionStats getCumnlativeStats() {
		return cumnlativeStats;
	}

	public void setCumnlativeStats(SessionStats cumnlativeStats) {
		this.cumnlativeStats = cumnlativeStats;
	}

	public SessionStats getCurrentStats() {
		return currentStats;
	}

	public void setCurrentStats(SessionStats currentStats) {
		this.currentStats = currentStats;
	}
}
