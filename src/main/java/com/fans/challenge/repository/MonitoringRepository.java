package com.fans.challenge.repository;

import com.fans.challenge.domain.MonitoringReport;
import com.fans.challenge.domain.Report;

public interface MonitoringRepository {

	public void save(Report report);

	public MonitoringReport find();

	public void clearData();
}
