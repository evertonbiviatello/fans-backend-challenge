package com.fans.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fans.challenge.service.MonitoringService;

@Controller
@RequestMapping(value = "/")
public class HomeController {

	@Autowired
	private MonitoringService monitoringService;

	@GetMapping
	public ModelAndView getReport(ModelMap model) {
		model.addAttribute("report", monitoringService.getReport());
		return new ModelAndView("/home", model);
	}

}
