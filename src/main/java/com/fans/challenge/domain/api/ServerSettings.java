package com.fans.challenge.domain.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class ServerSettings {

	@Min(value = 500, message = "Interval must be more than 500ms")
	@NotNull(message = "Interval must not be null!")
	private Long interval;

	@NotBlank(message = "Host name must not be blank!")
	private String hostname;

	public ServerSettings() {

	}

	public Long getInterval() {
		return interval;
	}

	public String getHostname() {
		return hostname;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

}
