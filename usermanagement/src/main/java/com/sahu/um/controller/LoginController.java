package com.sahu.um.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sahu.um.constants.LVNConstants;
import com.sahu.um.model.User;

@Controller
@RequestMapping("/login-process")
public class LoginController {
	
	@GetMapping("/login")
	public String showLoginPage() {
		return LVNConstants.LOGIN_PAGE;
	}

	@GetMapping("/showRegistration")
	public String showRegistrationPage(@ModelAttribute("user") User user) {
		return LVNConstants.REGISTRATION_PAGE;
	}
	
	
	
}
