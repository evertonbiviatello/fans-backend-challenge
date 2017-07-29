package com.fans.challenge.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.fans.challenge.domain.MonitoringReport;
import com.fans.challenge.domain.PingReport;
import com.fans.challenge.domain.api.ServerInformation;

public class MonitoringTask implements Runnable {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private RestTemplate restTemplate;
	private String hostname;
	private MonitoringReport monitoringReport;

	public MonitoringTask(RestTemplate restTemplate, MonitoringReport monitoringReport) {
		this.restTemplate = restTemplate;
		this.monitoringReport = monitoringReport;
	}

	public void updateHostname(String hostname) {
		this.hostname = hostname;
	}

	private String getHostname() {
		return hostname;
	}

	@Override
	public void run() {
		ServerInformation serverInfo = restTemplate.getForObject(getHostname(), ServerInformation.class);
		String status = serverInfo.getStatus();
		logger.info("status: " + status);
		monitoringReport.addReport(new PingReport(serverInfo.getStatus(), new Date()));
	}

}
