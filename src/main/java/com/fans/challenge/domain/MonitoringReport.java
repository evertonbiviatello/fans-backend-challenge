package com.fans.challenge.domain;

import java.util.ArrayList;
import java.util.List;

public class MonitoringReport {

	private List<Report> reportList;

	public MonitoringReport() {
		this.reportList = new ArrayList<>();
	}

	public void addReport(Report report) {
		reportList.add(report);
	}

	public List<Report> getReportList() {
		return reportList;
	}

	public void clearData() {
		reportList.clear();
	}
}
