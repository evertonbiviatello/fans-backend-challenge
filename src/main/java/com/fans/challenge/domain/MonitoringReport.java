package com.fans.challenge.domain;

import java.util.ArrayList;
import java.util.List;

public class MonitoringReport {

	private List<PingReport> reportList;

	public MonitoringReport() {
		this.reportList = new ArrayList<>();
	}

	public void addReport(PingReport pingReport) {
		reportList.add(pingReport);
	}

	public List<PingReport> getReportList() {
		return reportList;
	}

	public void clearData() {
		reportList.clear();
	}
}
