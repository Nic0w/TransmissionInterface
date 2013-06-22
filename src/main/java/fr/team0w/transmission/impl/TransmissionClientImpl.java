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

/**
 * @author nic0w
 *
 */
public class TransmissionClientImpl implements TransmissionClient {

	private final HttpClient httpClient;
	private final URI transmissionRPC;
	
	/**
	 * 
	 */
	public TransmissionClientImpl(HttpClient client, URI rpcURI) {
		
		this.httpClient = client;
		this.transmissionRPC = rpcURI;
		
	}

	@Override
	public TorrentAddRequest newTorrent(String torrent) {
		
		HttpPost addRequest = new HttpPost(transmissionRPC);
			
		return new TorrentAddRequestImpl(this.httpClient, addRequest, torrent);
	}

	@Override
	public TorrentBatch newBatch(Torrent... torrents) {
		// TODO Auto-generated method stub
		return null;
	}

}
