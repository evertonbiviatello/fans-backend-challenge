package com.fans.challenge.repository;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
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
		final Runnable pinger = new Runnable() {
			@Override
			public void run() {
				String status = "";
				double executionTime = 0.0;
				long start = System.nanoTime();

				try {
					ServerInformation serverInfo = restTemplate.getForObject(hostname, ServerInformation.class);
					status = serverInfo.getStatus();
				} catch (ResourceAccessException e) {
					status = "DOWN";
					logger.info("exception: {}, status: {} ", e.getMessage(), status);
				} catch (RestClientException e) {
					status = "UNAVAILABLE";
					logger.info("exception: {}, status: {} ", e.getMessage(), status);
				}
				long end = System.nanoTime();

				// Calculate the request time
				long elapsedTime = end - start;
				executionTime = (double) elapsedTime / 1000000.0;

				logger.info("status: {} in {} ms", status, executionTime);
				monitoringReport.addReport(new PingReport(status, new Date(), executionTime, hostname, interval));

			}
		};
		return taskScheduler.scheduleAtFixedRate(pinger, interval);
	}

	@Override
	public MonitoringReport findReport() {
		return monitoringReport;
	}
}
