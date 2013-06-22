/**
 * 
 */
package fr.team0w.transmission.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import fr.team0w.transmission.iface.Torrent;
import fr.team0w.transmission.iface.TorrentAddRequest;
import fr.team0w.transmission.iface.TorrentBatch;
import fr.team0w.transmission.iface.TransmissionClient;
import fr.team0w.transmission.iface.TransmissionSessionIDManager;

/**
 * @author nic0w
 *
 */
public class TransmissionClientImpl implements TransmissionClient, TransmissionSessionIDManager {

	private final HttpClient httpClient;
	private final URI transmissionRPC;
	
	private String transmissionSessionID;
	
	/**
	 * 
	 */
	public TransmissionClientImpl(HttpClient client, URI rpcURI) {
		
		this.httpClient = client;
		this.transmissionRPC = rpcURI;
		
		this.transmissionSessionID = "";
		
	}

	@Override
	public TorrentAddRequest newTorrent(String torrent) {
		
		HttpPost addRequest = new HttpPost(transmissionRPC);
			
		return new TorrentAddRequestImpl(this, this.httpClient, addRequest, torrent);
	}

	@Override
	public TorrentBatch newBatch(Torrent... torrents) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTransmissionSessionID(String id) {
		this.transmissionSessionID = id;
	}

	@Override
	public String getTransmissionSessionID() {
		return this.getTransmissionSessionID();
	}

}
