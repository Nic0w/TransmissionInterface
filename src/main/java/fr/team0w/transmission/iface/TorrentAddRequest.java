package fr.team0w.transmission.iface;

import java.io.IOException;

public interface TorrentAddRequest {

	/**
	 * The format of the "cookies" should be NAME=CONTENTS,
	 * where NAME is the cookie name and CONTENTS is what the cookie should contain. 
	 * Set multiple cookies like this: "name1=content1; name2=content2;" etc. 
	 * 
	 * http://curl.haxx.se/libcurl/c/curl_easy_setopt.html#CURLOPTCOOKIE
	 * 
	 * @param name The name of the cookie
	 * @param content The content of the cookie
	 * @return
	 */
	public TorrentAddRequest addCookie(String name, String content);
	
	/**
	 * @param path Path to download the torrent to.
	 * @return
	 */
	public TorrentAddRequest setDownloadDirectory(String path);
	
	/**
	 * @param pause If true, don't start the torrent
	 * @return
	 */
	public TorrentAddRequest pauseAtStart(boolean pause);
	
	/**
	 * @param maxPeers Maximum number of peers
	 * @return
	 */
	public TorrentAddRequest setPeerLimit(Number maxPeers);
	
	/**
	 * @param torrentBandwithPriority
	 * @return
	 */
	public TorrentAddRequest setBandwithPriority(Number torrentBandwithPriority);
	
	/**
	 * @param fileIDs
	 * @return
	 */
	public TorrentAddRequest setWantedFiles(int... fileIDs);
	
	/**
	 * @param fileIDs
	 * @return
	 */
	public TorrentAddRequest setUnwantedFiles(int... fileIDs);
	
	/**
	 * @return The newly created Torrent
	 * @throws TransmissionException 
	 * @throws IOException 
	 */
	public Torrent add() throws IOException, TransmissionException;
}
