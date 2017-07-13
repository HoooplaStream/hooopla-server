package fr.cseries.transmission.response;

public class SessionStatsResponse extends TransmissionResponse {
	private SessionStatsResponseArguments arguments;

	public SessionStatsResponseArguments getArguments() {
		return arguments;
	}

	public void setArguments(SessionStatsResponseArguments arguments) {
		this.arguments = arguments;
	}
}
