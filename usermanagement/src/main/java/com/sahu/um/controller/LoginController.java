package com.sahu.um.controller;

import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sahu.um.constants.LVNConstants;
import com.sahu.um.constants.LoginConstants;
import com.sahu.um.model.User;
import com.sahu.um.service.UserService;

@Controller
public class LoginController {
	
	private final Logger LOGGER = Logger.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
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
	
	@PostMapping("/registration")
	public String registrationProcess(Map<String, Object> map, @ModelAttribute("user") User user) {
		LOGGER.debug("Inside registrationProcess() method");
		LOGGER.info("User data - "+user.getEmail());
		if (user.getEmail() != null) {
			Optional<User> isExist = userService.findByEmail(user.getEmail());
			
			if (isExist.isPresent()) {
				map.put(LoginConstants.REGISTRATION_ERROR, environment.getProperty("duplicate_user_error_msg"));
				return LVNConstants.REGISTRATION_PAGE;
			}
			else {
				Long registeredUserId = userService.registerUser(user);
				if (registeredUserId!=null) {
					map.put(LoginConstants.REGISTRATION_SUCCESS, environment.getProperty("registration_success_msg"));
				}
				else {
					map.put(LoginConstants.REGISTRATION_ERROR, environment.getProperty("registration_failed_msg"));
				}
			}
		}
		
		return LVNConstants.LOGIN_PAGE;
	}

	@GetMapping("/forgetpassword")
	public String showForgetPasswordPage(@ModelAttribute("user") User user) {
		return LVNConstants.FORGET_PASSWORD_PAGE;
	}
	
}
