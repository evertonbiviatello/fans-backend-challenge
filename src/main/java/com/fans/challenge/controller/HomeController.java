package com.fans.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fans.challenge.service.MonitoringService;

@Controller
public class HomeController {

	@Autowired
	private MonitoringService monitoringService;

	@Value("${application.ping.default.hostname}")
	private String DEFAULT_HOSTNAME;
	@Value("${application.ping.default.interval}")
	private Long DEFAULT_INTERVAL;
	
	@GetMapping("/")
	public ModelAndView getReport(ModelMap model) {
		model.addAttribute("report", monitoringService.getReport());
		model.put("defaultHostname", DEFAULT_HOSTNAME);
		model.put("defaultInterval", DEFAULT_INTERVAL);
		return new ModelAndView("home", model);
	}

}
