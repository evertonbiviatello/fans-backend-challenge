package com.fans.challenge.domain;

import java.util.Date;

public class PingReport {

	private String status;
	private Date date;
	private Double executionTime;
	private String hostname;
	private Long interval;

	public PingReport(String status, Date date, Double executionTime, String hostname, Long interval) {
		this.status = status;
		this.date = date;
		this.executionTime = executionTime;
		this.hostname = hostname;
		this.interval = interval;
	}

	public String getStatus() {
		return status;
	}

	public Date getDate() {
		return date;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Double executionTime) {
		this.executionTime = executionTime;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

}
