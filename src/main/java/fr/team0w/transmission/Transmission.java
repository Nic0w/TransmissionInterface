/**
 * 
 */
package fr.team0w.transmission;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import fr.team0w.transmission.iface.TransmissionClient;
import fr.team0w.transmission.impl.TransmissionClientImpl;

/**
 * @author nic0w
 *
 */
public class Transmission {
	
	private static final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	
	/**
	 * 
	 */
	private Transmission() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static TransmissionClient connect(String host, int port, String path) throws URISyntaxException {
		
		HttpClient httpClient = httpClientBuilder.build();
		
		URIBuilder uriBuilder = new URIBuilder();
		
		URI rpcURI = uriBuilder.
						setHost(host).
						setPort(port).
						setPath(path).
					build();
	
		return new TransmissionClientImpl(httpClient, rpcURI);
	}

}
