/**
 * 
 */
package fr.team0w.transmission.iface;

/**
 * @author nic0w
 *
 */
public interface TransmissionClient {

	/**
	 * The arg must be the path or URL to the torrent file or the content of the torrent file base64 encoded.
	 *
	 * @param torrent
	 * @return
	 */
	public TorrentAddRequest newTorrent(String torrent);
	
	/**
	 * @param torrents
	 * @return
	 */
	public TorrentBatch newBatch(Torrent ...torrents);
	
	
}
