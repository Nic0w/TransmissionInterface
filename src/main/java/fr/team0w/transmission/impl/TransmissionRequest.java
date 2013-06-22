/**
 * 
 */
package fr.team0w.transmission.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fr.team0w.transmission.iface.TransmissionException;
import fr.team0w.transmission.iface.TransmissionSessionIDManager;

/**
 * @author nic0w
 *
 */
public abstract class TransmissionRequest {

	private static final String X_TRANSMISSION_SESSION_ID = "X-Transmission-Session-Id";
	private static final String RESULT_FIELD = "result";
	private static final String SUCCESS = "success";
	
	protected interface JsonifiableEnum {
		
		public String toJsonName();
		
	}
	
	private static final String   METHOD    = "method";
	protected static final String ARGUMENTS = "arguments";
	
	protected final ObjectMapper jsonMapper;

	private final TransmissionSessionIDManager sessionManager;
	
	private final HttpClient httpClient;
	private final HttpPost httpRequest;
	
	private final ObjectNode methodCall;
	private final ObjectNode methodArgs;
	/**
	 * @param sessionManager 
	 * 
	 */
	protected TransmissionRequest(TransmissionSessionIDManager sessionManager, HttpClient httpClient, HttpPost postRequest, TransmissionMethod transmissionMethod) {
		
		this.jsonMapper = new ObjectMapper();
		
		this.sessionManager = sessionManager;
		
		this.httpClient  = httpClient;
		this.httpRequest = postRequest;
		
		this.methodCall = this.jsonMapper.valueToTree(transmissionMethod);
		
		this.methodArgs = this.methodCall.putObject(ARGUMENTS);
	}
	
	
	protected void setArgument(JsonifiableEnum argName, JsonNode json) {
		
		this.methodArgs.put(argName.toJsonName(), json);
	}
	
	protected JsonNode getArgumentValue(JsonifiableEnum argName) {
		
		return this.methodArgs.get(argName.toJsonName());
	}
	
	/**
	 * 	 Most Transmission RPC servers require a X-Transmission-Session-Id header to be sent with requests, to prevent CSRF attacks. 
	 *   When your request has the wrong id -- such as when you send your first request, 
	 *   or when the server expires the CSRF token -- the Transmission RPC server will return an HTTP 409 error with the
	 *   right X-Transmission-Session-Id in its own headers. 
	 *   So, the correct way to handle a 409 response is to update your X-Transmission-Session-Id and to resend the previous request. 
	 * 
	 * @return The parsed content of the response 
	 * @throws IOException
	 * @throws TransmissionException 
	 */
	protected JsonNode execute() throws IOException, TransmissionException {
		
		HttpResponse response = null;
		
		do {
			
			if(response != null && response.containsHeader(X_TRANSMISSION_SESSION_ID))
				
				this.sessionManager.setTransmissionSessionID(
						response.getFirstHeader(X_TRANSMISSION_SESSION_ID).
							getValue()
					);
			
			this.httpRequest.setHeader(X_TRANSMISSION_SESSION_ID, this.sessionManager.getTransmissionSessionID());
			
			response = this.httpClient.execute(this.httpRequest);
			
		}
		while(response.getStatusLine().getStatusCode() == 409);
		
		StatusLine status = response.getStatusLine();
		
		if(status.getStatusCode() != 200)
			throw new HttpResponseException(status.getStatusCode(), status.getReasonPhrase());
		
		InputStream content = response.getEntity().getContent();
		
		JsonNode jsonResponse = this.jsonMapper.readTree(content);
		
		return checkSuccess(jsonResponse);
	}
	
	private JsonNode checkSuccess(JsonNode root) throws TransmissionException {
		
		JsonNode resultField = root.get(RESULT_FIELD);
		
		if(resultField == null)
			throw new NullPointerException(
				String.format("Field '%s' not found despite the response looking good !", RESULT_FIELD)
			);
		
		String resultMessage = resultField.textValue();
		
		if(!resultMessage.contentEquals(SUCCESS))
			throw new TransmissionException(resultMessage);
		
		return root.get(ARGUMENTS);
	}

}
