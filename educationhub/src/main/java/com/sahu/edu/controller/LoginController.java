package com.sahu.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sahu.edu.constants.LVNConstants;
import com.sahu.edu.model.User;

@Controller
public class LoginController {

	private Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private Environment environment;

	@GetMapping("/login")
	public String showLoginPage() {
		return LVNConstants.LOGIN_PAGE;
	}

	@GetMapping("/registration")
	public String showRegistrationPage(@ModelAttribute("user") User user) {
		return LVNConstants.REGISTRATION_PAGE;
	}

}
