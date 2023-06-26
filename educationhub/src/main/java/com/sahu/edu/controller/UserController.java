package com.sahu.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sahu.edu.constants.LVNConstants;
import com.sahu.edu.service.dto.CustomUserDetailsDTO;
import com.sahu.edu.util.security.SecurityUtil;

@Controller
@RequestMapping("/client/user")
public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/dashboard")
	public String showDashBoardPage() {
		CustomUserDetailsDTO loggedInUserDetails = SecurityUtil.getCurrentUser();
		LOGGER.info("Roles - " + loggedInUserDetails.getUserRoles());
		return LVNConstants.DASHBOARD_PAGE;
	}
	
}
