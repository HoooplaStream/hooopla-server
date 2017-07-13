package fr.cseries.transmission.request;

public class SessionSetRequest extends TransmissionRequest {
	public SessionSetRequest() {
		super(TransmissionRequestMethod.SESSION_SET);
	}

	public void setAltSpeedDown(long speed) {
		arguments.put("alt-speed-down", speed);
	}

	public void setAltSpeedEnabled(boolean enabled) {
		arguments.put("alt-speed-enabled", enabled);
	}
}
