package com.sahu.um.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sahu.um.constants.LVNConstants;
import com.sahu.um.service.dto.CustomUserDetailsDTO;
import com.sahu.um.util.security.SecurityUtil;

@Controller
@RequestMapping("/client/user")
public class UserController {

	private final Logger LOGGER = Logger.getLogger(UserController.class);
	
	@GetMapping("/dashboard")
	public String showDashBoardPage() {
		CustomUserDetailsDTO loggedInUserDetails = SecurityUtil.getCurrentUser();
		LOGGER.info("Roles - "+loggedInUserDetails.getUserRoles());
		return LVNConstants.DASHBOARD_PAGE;
	}
	
	@GetMapping("/list")
	public String showUserListPage() {
		return LVNConstants.USER_LIST_PAGE;
	}
	
	@GetMapping("/add")
	public String showAddUserPage() {
		return LVNConstants.ADD_EDIT_USER_PAGE;
	}
	
	@GetMapping("/assign-role")
	public String showAssignRolePage() {
		return LVNConstants.ASSIGN_ROLE_PAGE;
	}
	
}
