package fr.cseries.transmission.request;

public class TorrentAddRequest extends TransmissionRequest {

    public TorrentAddRequest(String metainfo) {
        super(TransmissionRequestMethod.TORRENT_ADD);
        arguments.put("metainfo", metainfo);
    }

    public void setPausedOnAdd() {
        arguments.put("paused", true);
    }
}
