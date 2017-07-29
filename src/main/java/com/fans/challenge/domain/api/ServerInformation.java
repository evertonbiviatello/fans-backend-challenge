package com.fans.challenge.domain.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerInformation {

	@JsonProperty("status")
	private String status;

	public ServerInformation() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = (status != null) ? status : "DOWN";
	}

}
