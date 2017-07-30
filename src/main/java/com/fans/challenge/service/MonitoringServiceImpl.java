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
import com.fans.challenge.repository.MonitoringRepository;
import com.fans.challenge.task.MonitoringTask;

/**
 * Service class that manages the scheduling of a single
 * <code>MonitoringTask</code>.
 * 
 * @author Everton Biviatello
 *
 */
@Service
public class MonitoringServiceImpl implements MonitoringService {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private TaskScheduler taskScheduler;
	private MonitoringRepository monitoringRepository;
	private RestTemplate restTemplate;

	private ScheduledFuture<?> scheduledFuture;
	private MonitoringTask monitoringTask;

	@Autowired
	public MonitoringServiceImpl(RestTemplateBuilder restTemplateBuilder, TaskScheduler taskScheduler, MonitoringRepository monitoringRepository) {
		this.taskScheduler = taskScheduler;
		this.monitoringRepository = monitoringRepository;
		this.restTemplate = restTemplateBuilder.build();
		this.monitoringTask = new MonitoringTask(restTemplate, monitoringRepository);
	}

	/**
	 * Starts the monitoring by using <code>MonitoringTask</code> and updates the
	 * <strong>hostname</strong> and <strong>interval.</strong>
	 * <p>
	 * It also makes sure only one task is running.
	 * 
	 * @param hostname
	 *            an URL giving the hostname to request
	 * @param interval
	 *            the interval on which we will be requesting
	 */
	@Override
	public boolean start(String hostname, Long interval) {
		boolean hasStarted = false;

		monitoringTask.updateHostname(hostname);
		monitoringTask.updateInterval(interval);

		if (scheduledFuture == null) {
			logger.info("1st time");
			scheduledFuture = taskScheduler.scheduleAtFixedRate(monitoringTask, interval);
			hasStarted = true;
		} else {
			if (scheduledFuture.isDone()) {
				logger.info("isDone.. recreate");
				scheduledFuture = taskScheduler.scheduleAtFixedRate(monitoringTask, interval);
				hasStarted = true;
			}
		}
		return hasStarted;
	}

	/**
	 * Method to stop the execution of <code>MonitoringTask</code>.
	 */
	@Override
	public void stop() {
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
		}
	}

	/**
	 * Method to return a <code>MonitoringReport</code>.
	 */
	@Override
	public MonitoringReport getReport() {
		return monitoringRepository.find();
	}

	/**
	 * Clear the reports.
	 */
	@Override
	public void clearReport() {
		monitoringRepository.clearData();
	}
}
