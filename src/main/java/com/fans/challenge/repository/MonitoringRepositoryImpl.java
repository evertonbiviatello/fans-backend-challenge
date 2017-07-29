package com.fans.challenge.repository;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fans.challenge.domain.MonitoringReport;
import com.fans.challenge.domain.PingReport;
import com.fans.challenge.domain.api.ServerInformation;

@Repository
public class MonitoringRepositoryImpl implements MonitoringRepository {

	@Autowired
	private TaskScheduler taskScheduler;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private MonitoringReport monitoringReport;
	private RestTemplate restTemplate;

	public MonitoringRepositoryImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
		this.monitoringReport = new MonitoringReport();
	}

	@Override
	public ScheduledFuture<?> getData(String hostname, Long interval) {
		return taskScheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				ServerInformation serverInfo = restTemplate.getForObject(hostname, ServerInformation.class);
				logger.info("status: " + serverInfo.getStatus());
				monitoringReport.addReport(new PingReport(serverInfo.getStatus(), new Date()));
			}
		}, interval);
	}

	@Override
	public MonitoringReport findReport() {
		return monitoringReport;
	}
}
