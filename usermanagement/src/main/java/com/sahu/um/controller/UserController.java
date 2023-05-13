package com.sahu.um.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sahu.um.constants.LVNConstants;
import com.sahu.um.security.util.SecurityUtil;
import com.sahu.um.service.dto.CustomUserDetailsDTO;

@Controller
@RequestMapping("/client/user")
public class UserController {

	private final Logger LOGGER = Logger.getLogger(UserController.class);
	
	@GetMapping("/dashboard")
	public String dashBoardPage() {
		CustomUserDetailsDTO loggedInUserDetails = SecurityUtil.getCurrentUser();
		LOGGER.info("Roles - "+loggedInUserDetails.getUserRoles());
		//LOGGER.info("Permissions - "+loggedInUserDetails.getUserPermissions());
		//LOGGER.info("Check For Global Admin - "+SecurityUtil.isGlobalAdmin(loggedInUserDetails));
		return LVNConstants.DASHBOARD_PAGE;
	}
	
}
