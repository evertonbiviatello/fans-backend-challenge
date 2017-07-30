package com.fans.challenge.repository;

import org.springframework.stereotype.Repository;

import com.fans.challenge.domain.MonitoringReport;
import com.fans.challenge.domain.Report;

@Repository
public class MonitoringRepositoryImpl implements MonitoringRepository {

	private MonitoringReport monitoringReport;

	public MonitoringRepositoryImpl() {
		this.monitoringReport = new MonitoringReport();
	}

	@Override
	public void save(Report report) {
		monitoringReport.addReport(report);
	}

	@Override
	public MonitoringReport find() {
		return monitoringReport;
	}

	@Override
	public void clearData() {
		monitoringReport.clearData();
	}

}
