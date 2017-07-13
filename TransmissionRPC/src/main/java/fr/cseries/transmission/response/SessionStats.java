package fr.cseries.transmission.response;

public class SessionStats {
	private long uploadedBytes;
	private long downloadedBytes;
	private long filesAdded;
	private long sessionCount;
	private long secondsActive;

	public long getUploadedBytes() {
		return uploadedBytes;
	}

	public void setUploadedBytes(long uploadedBytes) {
		this.uploadedBytes = uploadedBytes;
	}

	public long getDownloadedBytes() {
		return downloadedBytes;
	}

	public void setDownloadedBytes(long downloadedBytes) {
		this.downloadedBytes = downloadedBytes;
	}

	public long getFilesAdded() {
		return filesAdded;
	}

	public void setFilesAdded(long filesAdded) {
		this.filesAdded = filesAdded;
	}

	public long getSessionCount() {
		return sessionCount;
	}

	public void setSessionCount(long sessionCount) {
		this.sessionCount = sessionCount;
	}

	public long getSecondsActive() {
		return secondsActive;
	}

	public void setSecondsActive(long secondsActive) {
		this.secondsActive = secondsActive;
	}
}
