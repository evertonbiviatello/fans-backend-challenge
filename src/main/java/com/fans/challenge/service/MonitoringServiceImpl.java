package com.fans.challenge.service;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fans.challenge.domain.MonitoringReport;
import com.fans.challenge.task.MonitoringTask;

@Service
public class MonitoringServiceImpl implements MonitoringService {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private TaskScheduler taskScheduler;

	private MonitoringTask monitorTask;
	private MonitoringReport monitoringReport;
	private ScheduledFuture<?> scheduledFuture;
	private RestTemplate restTemplate;

	public MonitoringServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
		this.monitoringReport = new MonitoringReport();
		this.monitorTask = new MonitoringTask(restTemplate, monitoringReport);
	}

	@Override
	public void start(String hostname, Long interval) {
		monitorTask.updateHostname(hostname);

		if (scheduledFuture == null) {
			logger.info("1st time");
			scheduledFuture = taskScheduler.scheduleAtFixedRate(monitorTask, interval);
		} else {
			if (scheduledFuture.isDone()) {
				logger.info("isDone.. recreate");
				scheduledFuture = taskScheduler.scheduleAtFixedRate(monitorTask, interval);
			}
		}
	}

	@Override
	public void stop() {
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
			// monitoringReport.clearData();
		}
	}

	@Override
	public MonitoringReport getReport() {
		return monitoringReport;
	}

}
