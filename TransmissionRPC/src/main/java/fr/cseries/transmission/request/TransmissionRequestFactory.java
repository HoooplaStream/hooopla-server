package fr.cseries.transmission.request;

import java.util.List;

public class TransmissionRequestFactory {

    public static TorrentStartRequest getStartRequest(List<Integer> ids) {
        return new TorrentStartRequest(ids);
    }

    public static TorrentStartRequest getStartAllReuqest() {
        return new TorrentStartRequest(null);
    }

    public static TorrentStopRequest getStopRequest(List<Integer> ids) {
        return new TorrentStopRequest(ids);
    }

    public static TorrentStopRequest getStopAllRequest() {
        return new TorrentStopRequest(null);
    }

    public static TransmissionRequest getAddRequest(String metainfo) {
        return new TorrentAddRequest(metainfo);
    }

    public static TorrentRemoveRequest getRemoveAllRequest() {
        return new TorrentRemoveRequest(null, true);
    }

    public static TorrentRemoveRequest getRemoveRequest(List<Integer> ids) {
        return new TorrentRemoveRequest(ids, true);
    }

    public static TransmissionRequest getGetRequest(List<Integer> ids) {
        return new TorrentGetRequest(ids);
    }

    public static TransmissionRequest getgetAllRequest() {
        return new TorrentGetRequest(null);
    }
}
