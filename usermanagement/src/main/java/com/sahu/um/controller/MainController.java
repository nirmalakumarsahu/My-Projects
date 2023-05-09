package com.sahu.um.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sahu.um.constants.LVNConstants;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String showLoginPage() {
		return LVNConstants.INDEX_PAGE;
	}
	
}
