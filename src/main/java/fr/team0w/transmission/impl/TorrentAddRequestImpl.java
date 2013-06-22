/**
 * 
 */
package fr.team0w.transmission.impl;

import static fr.team0w.transmission.impl.TorrentAddRequestImpl.Args.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import fr.team0w.transmission.iface.Torrent;
import fr.team0w.transmission.iface.TorrentAddRequest;
import fr.team0w.transmission.iface.TransmissionException;
import fr.team0w.transmission.iface.TransmissionSessionIDManager;

/**
 * @author nic0w
 *
 */
public class TorrentAddRequestImpl extends TransmissionRequestWithResult implements TorrentAddRequest {

	enum Args implements TransmissionRequest.JsonifiableEnum {
		
		   COOKIES           , //pointer to a string of one or more cookies.
		   DOWNLOAD_DIR      , //path to download the torrent to
		   FILENAME          , //filename or URL of the .torrent file
		   METAINFO          , //base64-encoded .torrent content
		   PAUSED            , //if true, don't start the torrent
		   PEER_LIMIT        , //maximum number of peers
		   BANDWIDTHPRIORITY , //torrent's bandwidth tr_priority_t 
		   FILES_WANTED      , //indices of file(s) to download
		   FILES_UNWANTED    , //indices of file(s) to not download
		   PRIORITY_HIGH     , //indices of high-priority file(s)
		   PRIORITY_LOW      , //indices of low-priority file(s)
		   PRIORITY_NORMAL   ; //indices of normal-priority file(s)

		@Override
		public String toJsonName() {
			return this.toString().replace('_', '-').toLowerCase();
		}     
		
	}
	
	private static final String TORRENT_ADDED = "torrent-added";
	
	private static final Pattern BASE64_PATTERN = Pattern.compile(
			"^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$"
			);


	public TorrentAddRequestImpl(TransmissionSessionIDManager sessionManager, HttpClient httpClient, HttpPost addRequest, String torrent) {

		super(sessionManager, httpClient, addRequest, TransmissionMethod.TORRENT_ADD, TORRENT_ADDED);

		Matcher base64Matcher = BASE64_PATTERN.matcher(torrent);

		if(base64Matcher.matches())
			super.setArgument(METAINFO, TextNode.valueOf(torrent));
		else
			super.setArgument(FILENAME, TextNode.valueOf(torrent));
	}

	@Override
	public TorrentAddRequest addCookie(String name, String content) {
		
		String cookies = super.getArgumentValue(COOKIES).asText();
		
		if(cookies == null)
			cookies = "";
		
		cookies += String.format("%s=%s; ", name, content);
		
		super.setArgument(COOKIES, TextNode.valueOf(cookies));
		
		return this;
	}

	@Override
	public TorrentAddRequest setDownloadDirectory(String path) {
		
		super.setArgument(DOWNLOAD_DIR, TextNode.valueOf(path));
		
		return this;
	}


	@Override
	public TorrentAddRequest pauseAtStart(boolean pause) {
		
		super.setArgument(PAUSED, BooleanNode.valueOf(pause));
		
		return this;
	}

	@Override
	public TorrentAddRequest setPeerLimit(Number maxPeers) {
		
		super.setArgument(PEER_LIMIT, IntNode.valueOf(maxPeers.intValue()));
		
		return this;
	}


	@Override
	public TorrentAddRequest setBandwithPriority(Number torrentBandwithPriority) {
		
		super.setArgument(BANDWIDTHPRIORITY, IntNode.valueOf(torrentBandwithPriority.intValue()));
		
		return this;
	}

	@Override
	public TorrentAddRequest setWantedFiles(int... fileIDs) {
		// TODO Auto-generated method stub
		return this;
	}


	@Override
	public TorrentAddRequest setUnwantedFiles(int... fileIDs) {
		// TODO Auto-generated method stub
		return this;
	}


	@Override
	public Torrent add() throws IOException, TransmissionException {
		
		JsonNode torrentAdded = super.execute();
		
		String name = torrentAdded.get("name").textValue();
		
		String hashString = torrentAdded.get("hashString").textValue();
		
		int id = torrentAdded.get("id").asInt(); 
		
		return null;
	}

}
