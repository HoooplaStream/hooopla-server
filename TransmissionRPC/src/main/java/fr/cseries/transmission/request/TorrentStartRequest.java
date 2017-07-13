package fr.cseries.transmission.request;

import java.util.List;

public class TorrentStartRequest extends TransmissionRequest
{
    TorrentStartRequest(List<Integer> ids)
    {
        super(TransmissionRequestMethod.TORRENT_START);
        if(ids!=null)
            arguments.put("ids",ids);
    }
}

