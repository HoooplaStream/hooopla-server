package fr.cseries.transmission.request;

import java.util.HashMap;
import java.util.Map;

public class TransmissionRequest {
	protected String              method;
	protected Map<String, Object> arguments;

	public TransmissionRequest(String method) {
		this.method = method;
		arguments = new HashMap<>();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}
}
