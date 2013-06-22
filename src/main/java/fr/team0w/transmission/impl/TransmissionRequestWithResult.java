/**
 * 
 */
package fr.team0w.transmission.impl;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import com.fasterxml.jackson.databind.JsonNode;

import fr.team0w.transmission.iface.TransmissionException;
import fr.team0w.transmission.iface.TransmissionSessionIDManager;

/**
 * @author nic0w
 *
 */
public abstract class TransmissionRequestWithResult extends TransmissionRequest {
	
	private final String resultField;
	
	/**
	 * @param sessionManager
	 * @param httpClient
	 * @param postRequest
	 * @param transmissionMethod
	 */
	public TransmissionRequestWithResult(TransmissionSessionIDManager sessionManager, HttpClient httpClient, HttpPost postRequest, TransmissionMethod transmissionMethod, String resultField) {
		super(sessionManager, httpClient, postRequest, transmissionMethod);
		
		
		this.resultField = resultField;
		
	}

	@Override
	protected JsonNode execute() throws IOException, TransmissionException {
		
		JsonNode arguments = super.execute().get(super.ARGUMENTS);
		
		if(arguments == null)
			throw new NullPointerException(
					String.format("Field '%s' not found despite the response looking good !", super.ARGUMENTS)
				);
			
		return arguments.get(this.resultField);
	}


}
