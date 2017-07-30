package com.fans.challenge.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fans.challenge.domain.Report;
import com.fans.challenge.domain.api.ServerInformation;
import com.fans.challenge.repository.MonitoringRepository;

/**
 * 
 * <code>Runnable</code> class that requests from a given hostname over a
 * specific interval.
 * 
 * @author Everton Biviatello
 */
public class MonitoringTask implements Runnable {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private RestTemplate restTemplate;
	private MonitoringRepository monitoringRepository;
	private String hostname;
	private Long interval;

	public MonitoringTask(RestTemplate restTemplate, MonitoringRepository monitoringRepository) {
		this.restTemplate = restTemplate;
		this.monitoringRepository = monitoringRepository;
	}

	public void updateHostname(String hostname) {
		this.hostname = hostname;
	}

	public void updateInterval(Long interval) {
		this.interval = interval;
	}

	@Override
	public void run() {
		String status;
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

		// Calculate the request time in milliseconds
		long elapsedTime = end - start;
		double executionTime = (double) elapsedTime / 1000000.0;

		// Clear the log once we reach a high amount of data
		if (monitoringRepository.find().getReportList().size() > 500) {
			monitoringRepository.clearData();
		}

		logger.info("status: {} in {} ms", status, executionTime);
		monitoringRepository.save(new Report(status, new Date(), executionTime, hostname, interval));
	}

}
