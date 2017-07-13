package fr.cseries.transmission.request;

import java.util.List;

public class TorrentRemoveRequest extends TransmissionRequest {

	public TorrentRemoveRequest(List<Integer> ids, boolean deleteLocalData) {
		super(TransmissionRequestMethod.TORRENT_REMOVE);
		if (ids != null)
			arguments.put("ids", ids);
		if (deleteLocalData)
			arguments.put("delete-local-data", true);
	}
}
