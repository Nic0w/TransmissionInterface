/**
 * 
 */
package fr.team0w.transmission.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fr.team0w.transmission.iface.Torrent;
import fr.team0w.transmission.iface.TorrentAddRequest;

/**
 * @author nic0w
 *
 */
public class TorrentAddRequestImpl implements TorrentAddRequest {

	interface Arguments {
		
		   public static final String COOKIES           = "cookies";            //pointer to a string of one or more cookies.
		   public static final String DOWNLOAD_DIR      = "download-dir";       //path to download the torrent to
		   public static final String FILENAME          = "filename";           //filename or URL of the .torrent file
		   public static final String METAINFO          = "metainfo";           //base64-encoded .torrent content
		   public static final String PAUSED            = "paused";             //if true, don't start the torrent
		   public static final String PEER_LIMIT        = "peer-limit";         //maximum number of peers
		   public static final String BANDWIDTHPRIORITY = "bandwidthPriority";  //torrent's bandwidth tr_priority_t 
		   public static final String FILES_WANTED      = "files-wanted";       //indices of file(s) to download
		   public static final String FILES_UNWANTED    = "files-unwanted";     //indices of file(s) to not download
		   public static final String PRIORITY_HIGH     = "priority-high";      //indices of high-priority file(s)
		   public static final String PRIORITY_LOW      = "priority-low";       //indices of low-priority file(s)
		   public static final String PRIORITY_NORMAL   = "priority-normal";    //indices of normal-priority file(s)
		
	}
	
	private static final Pattern BASE64_PATTERN = Pattern.compile(
			"^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$"
			);

	private static final String METHOD    = "method";
	private static final String ARGUMENTS = "arguments";


	private final ObjectMapper jsonMapper;

	private final HttpClient httpClient;
	private final HttpPost httpRequest;

	private final ObjectNode methodCall;
	private final ObjectNode methodArgs;


	public TorrentAddRequestImpl(HttpClient httpClient, HttpPost addRequest, String torrent) {

		this.jsonMapper = new ObjectMapper();

		this.httpClient  = httpClient;
		this.httpRequest = addRequest;

		this.methodCall = this.jsonMapper.valueToTree(TransmissionMethod.TORRENT_ADD);

		this.methodArgs = this.methodCall.putObject(ARGUMENTS);

		Matcher base64Matcher = BASE64_PATTERN.matcher(torrent);

		if(base64Matcher.matches())
			this.methodArgs.put(Arguments.METAINFO, torrent);
		else
			this.methodArgs.put(Arguments.FILENAME, torrent);
	}

	@Override
	public TorrentAddRequest addCookie(String name, String content) {
		
		String cookies = this.methodArgs.get(Arguments.COOKIES).asText();
		
		if(cookies == null)
			cookies = "";
		
		cookies += String.format("%s=%s; ", name, content);
		
		this.methodArgs.put(Arguments.COOKIES, cookies);
		
		return this;
	}

	@Override
	public TorrentAddRequest setDownloadDirectory(String path) {
		
		this.methodArgs.put(Arguments.DOWNLOAD_DIR, path);
		
		return this;
	}


	@Override
	public TorrentAddRequest pauseAtStart(boolean pause) {
		
		this.methodArgs.put(Arguments.PAUSED, pause);
		
		return this;
	}

	@Override
	public TorrentAddRequest setPeerLimit(Number maxPeers) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TorrentAddRequest setBandwithPriority(Number torrentBandwithPriority) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TorrentAddRequest setWantedFiles(int... fileIDs) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TorrentAddRequest setUnwantedFiles(int... fileIDs) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Torrent add() {
		// TODO Auto-generated method stub
		return null;
	}

}
