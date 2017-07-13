package fr.cseries.transmission.request;

import java.util.LinkedList;
import java.util.List;

public class TorrentGetRequest extends TransmissionRequest {

	public TorrentGetRequest(List<Integer> ids) {
		super(TransmissionRequestMethod.TORRENT_GET);
		if (ids != null)
			arguments.put("ids", ids);

		LinkedList<String> fields = new LinkedList<>();
		fields.add("id");
		fields.add("name");
		fields.add("isFinished");
		fields.add("downloadDir");
		fields.add("activityDate");
		fields.add("addedDate");
		fields.add("doneDate");
		fields.add("dateCreated");
		fields.add("bandwidthPriority");
		fields.add("creator");
		fields.add("desiredAvailable");
		fields.add("files");
		fields.add("isStalled");
		fields.add("magnetLink");
		fields.add("rateDownload");
		fields.add("rateUpload");
		fields.add("secondsDownloading");
		fields.add("secondsSeeding");
		fields.add("totalSize");
		fields.add("uploadRatio");
		fields.add("downloadedEver");
		fields.add("percentDone");
		fields.add("status");
		arguments.put("fields", fields);
	}
}
