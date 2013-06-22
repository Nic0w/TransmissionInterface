/**
 * 
 */
package fr.team0w.transmission.impl;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author nic0w
 *
 */
public enum TransmissionMethod {

	TORRENT_ADD,
	TORRENT_REMOVE,
	TORRENT_GET,
	TORRENT_SET;

	@JsonProperty("method")
	public String getMethodName() {
		return this.toString().replace('_', '-').toLowerCase();
	}
	
}
