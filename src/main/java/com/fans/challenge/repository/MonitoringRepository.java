package com.fans.challenge.repository;

import java.util.concurrent.ScheduledFuture;

import com.fans.challenge.domain.MonitoringReport;

public interface MonitoringRepository {

	public ScheduledFuture<?> getData(String hostname, Long interval);

	public MonitoringReport findReport();
}
