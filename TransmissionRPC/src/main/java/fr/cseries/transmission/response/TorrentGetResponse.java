package fr.cseries.transmission.response;

public class TorrentGetResponse extends TransmissionResponse {
	private TorrentGetResponseArguments arguments;

	public TorrentGetResponseArguments getArguments() {
		return arguments;
	}

	public void setArguments(TorrentGetResponseArguments arguments) {
		this.arguments = arguments;
	}
}
