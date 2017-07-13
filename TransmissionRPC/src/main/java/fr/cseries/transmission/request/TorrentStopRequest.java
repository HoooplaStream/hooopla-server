package fr.cseries.transmission.request;

import java.util.List;

public class TorrentStopRequest extends TransmissionRequest {
	TorrentStopRequest(List<Integer> ids) {
		super(TransmissionRequestMethod.TORRENT_STOP);
		if (ids != null)
			arguments.put("ids", ids);
	}
}

