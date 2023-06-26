package com.sahu.edu.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sahu.edu.constants.EducationHubConstants;
import com.sahu.edu.constants.LVNConstants;
import com.sahu.edu.model.User;
import com.sahu.edu.service.UserService;
import com.sahu.edu.service.dto.CustomUserDetailsDTO;
import com.sahu.edu.util.security.SecurityUtil;

@Controller
public class LoginController {

	private Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private Environment environment;

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String showHomePage() {
		return LVNConstants.INDEX_PAGE;
	}
	
	@GetMapping("/login")
	public String showLoginPage() {
		return LVNConstants.LOGIN_PAGE;
	}

	@GetMapping("/registration")
	public String showRegistrationPage(@ModelAttribute("user") User user) {
		return LVNConstants.REGISTRATION_PAGE;
	}
	
	@PostMapping("/registration")
	public String registrationProcess(RedirectAttributes redirectAttributes, @ModelAttribute("user") User user) {
		LOGGER.debug("Inside registrationProcess() method");
		LOGGER.info("User data - " + user.getEmail());
		if (user.getEmail() != null) {
			Optional<User> isExist = userService.findByEmail(user.getEmail());

			if (isExist.isPresent()) {
				redirectAttributes.addFlashAttribute(EducationHubConstants.REGISTRATION_ERROR, environment.getProperty("duplicate_user_error_msg"));
				return LVNConstants.REDIRECT_REGISTRATION_PAGE;
			} else {
				Long registeredUserId = userService.registerUser(user);
				if (registeredUserId != null) {
					redirectAttributes.addFlashAttribute(EducationHubConstants.REGISTRATION_SUCCESS, environment.getProperty("registration_success_msg"));
				} else {
					redirectAttributes.addFlashAttribute(EducationHubConstants.REGISTRATION_ERROR, environment.getProperty("registration_failed_msg"));
				}
			}
		}

		return LVNConstants.REDIRECT_LOGIN_PAGE;
	}
	
}
