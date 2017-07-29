package com.fans.challenge.service;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fans.challenge.domain.MonitoringReport;
import com.fans.challenge.repository.MonitoringRepository;

@Service
public class MonitoringServiceImpl implements MonitoringService {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	private MonitoringRepository monitoringRepository;
	private ScheduledFuture<?> scheduledFuture;

	@Override
	public void start(String hostname, Long interval) {
		if (scheduledFuture == null) {
			logger.info("1st time");
			scheduledFuture = monitoringRepository.getData(hostname, interval);
		} else {
			if (scheduledFuture.isDone()) {
				logger.info("isDone.. recreate");
				scheduledFuture = monitoringRepository.getData(hostname, interval);
			}
		}
	}

	@Override
	public void stop() {
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
		}
	}

	@Override
	public MonitoringReport getReport() {
		return monitoringRepository.findReport();
	}

	@Override
	public void clearReport() {
		monitoringRepository.findReport().clearData();
	}

}
