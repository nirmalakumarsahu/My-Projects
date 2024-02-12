package com.sahu.digital.marketing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sahu.digital.marketing.constants.LVNConstants;
import com.sahu.digital.marketing.constants.PermissionConstants;
import com.sahu.digital.marketing.security.SecurityUtil;
import com.sahu.digital.marketing.service.dto.CustomUserDetailsDTO;

@Controller
public class HomeController {

	private Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@GetMapping("/")
	public String showIndexPage() {
		CustomUserDetailsDTO customUserDetailsDTO = SecurityUtil.getCurrentUser();

		if (customUserDetailsDTO != null) {
			LOGGER.info("Custom User - " + customUserDetailsDTO.getEmail());

			if (customUserDetailsDTO.hasPermission(PermissionConstants.GLOBAL_ADMINISTRATION))
				return LVNConstants.REDIRECT_ADMIN_DASHBOARD;
			else
				return LVNConstants.REDIRECT_USER_DASHBOARD;
		}

		return LVNConstants.INDEX_PAGE;
	}

	@GetMapping("/dashboard")
	public String showUserDashBoad() {
		return LVNConstants.USER_DASHBOARD_PAGE;
	}

	@GetMapping("/admin/dashboard")
	public String showAdminDashBoad() {
		return LVNConstants.ADMIN_DASHBOARD_PAGE;
	}

}
