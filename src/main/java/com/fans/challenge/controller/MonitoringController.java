package com.fans.challenge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fans.challenge.controller.validator.ValidationError;
import com.fans.challenge.controller.validator.ValidationErrorBuilder;
import com.fans.challenge.domain.MonitoringReport;
import com.fans.challenge.domain.api.ServerSettings;
import com.fans.challenge.service.MonitoringService;

@RestController
public class MonitoringController {

	@Value("${application.ping.default.hostname}")
	private String DEFAULT_HOSTNAME;
	@Value("${application.ping.default.interval}")
	private Long DEFAULT_INTERVAL;

	@Autowired
	private MonitoringService monitoringService;

	// Web purposes only
	@GetMapping(value = "/start")
	public ResponseEntity<String> start() {
		String result = startMonitoring(null, null);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@PostMapping(value = "/start")
	public ResponseEntity<String> start(@Valid @RequestBody ServerSettings settings) {
		String result = startMonitoring(settings.getHostname(), settings.getInterval());
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	private String startMonitoring(String hostname, Long interval) {
		if (hostname == null) {
			hostname = DEFAULT_HOSTNAME;
		}
		if (interval == null) {
			interval = DEFAULT_INTERVAL;
		}

		boolean hasStarted = monitoringService.start(hostname, interval);

		String result;
		if (hasStarted) {
			result = "Monitoring started.";
		} else {
			result = "Monitoring already running.";
		}
		return result;
	}

	@GetMapping("/stop")
	public ResponseEntity<String> stop() {
		monitoringService.stop();
		return new ResponseEntity<String>("Monitoring stopped.", HttpStatus.OK);
	}

	@GetMapping(value = "/status")
	public ResponseEntity<MonitoringReport> statusMonitoring() {
		return new ResponseEntity<MonitoringReport>(monitoringService.getReport(), HttpStatus.OK);
	}

	@GetMapping(value = "/clear")
	public ResponseEntity<String> clearMonitoring() {
		monitoringService.clearReport();
		return new ResponseEntity<String>("Monitoring logs cleared.", HttpStatus.OK);
	}

	/**
	 * Validates monitoring start post request.
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ValidationError handleException(MethodArgumentNotValidException exception) {
		return ValidationErrorBuilder.fromBindingErrors(exception.getBindingResult());
	}

}
