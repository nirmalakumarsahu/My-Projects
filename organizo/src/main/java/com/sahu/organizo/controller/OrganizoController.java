package com.sahu.organizo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sahu.organizo.constants.LVNConstants;

@Controller
public class OrganizoController {
	
	@GetMapping("/")
	public String showHome() {
		return LVNConstants.INDEX_PAGE;
	}
	
	@GetMapping("/dashboard")
	public String showDashboard() {
		return LVNConstants.DASHBOARD_PAGE;
	}
}
