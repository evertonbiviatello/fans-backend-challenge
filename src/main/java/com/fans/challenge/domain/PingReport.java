package com.fans.challenge.domain;

import java.util.Date;

public class PingReport {

	private String status;
	private Date date;

	public PingReport(String status, Date date) {
		this.status = status;
		this.date = date;
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

}
