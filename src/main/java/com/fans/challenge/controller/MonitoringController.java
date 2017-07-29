package com.fans.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fans.challenge.domain.MonitoringReport;
import com.fans.challenge.service.MonitoringService;

@RestController
public class MonitoringController {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private MonitoringService monitoringService;

	@Value(value = "${application.ping.default.interval}")
	private Long DEFAULT_INTERVAL;
	@Value(value = "${application.ping.default.hostname}")
	private String DEFAULT_HOSTNAME;

	@GetMapping(value = "/start")
	public ResponseEntity<Void> start(
			@RequestParam(name = "interval", required = false) Long interval,
			@RequestParam(name = "hostname", required = false) String hostname) {// change to @RequestBody

		if (interval == null) {
			interval = DEFAULT_INTERVAL;
		}

		if (hostname == null) {
			hostname = DEFAULT_HOSTNAME;
		}

		monitoringService.start(hostname, interval);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/stop")
	public ResponseEntity<Void> stop() {
		monitoringService.stop();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping(value = "/status")
	public ResponseEntity<MonitoringReport> statusMonitoring() {
		return new ResponseEntity<MonitoringReport>(monitoringService.getReport(), HttpStatus.OK);
	}

	@GetMapping(value = "/clear")
	public ResponseEntity<Void> clearMonitoring() {
		monitoringService.clearReport();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
