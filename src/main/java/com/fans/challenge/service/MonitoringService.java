package com.fans.challenge.service;

import com.fans.challenge.domain.MonitoringReport;

public interface MonitoringService {

	public boolean start(String hostname, Long interval);

	public void stop();

	public MonitoringReport getReport();

	public void clearReport();

}
