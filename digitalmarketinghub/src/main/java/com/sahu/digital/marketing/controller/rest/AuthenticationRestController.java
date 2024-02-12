package com.sahu.digital.marketing.controller.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahu.digital.marketing.constants.PropertyKeyConstants;
import com.sahu.digital.marketing.dto.request.UserDTO;
import com.sahu.digital.marketing.model.User;
import com.sahu.digital.marketing.model.request.LoginDTO;
import com.sahu.digital.marketing.service.UserService;
import com.sahu.digital.marketing.service.dto.EmailPhoneDTO;
import com.sahu.digital.marketing.util.CommonsUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

	private Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private Environment environment;
	
	@Autowired
	private CommonsUtil commonsUtil;

	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO user, HttpServletRequest request) {
		LOGGER.debug("Inside registrationProcess() method");
		LOGGER.info("User data - " + user.getEmailorPhoneNo());
		
		EmailPhoneDTO emailPhoneDTO = commonsUtil.checkEmailOrPhoneNo(user.getEmailorPhoneNo());
		User isUserExist = userService.findByEmailOrPhoneNo(emailPhoneDTO.getEmail(), emailPhoneDTO.getPhoneNo());
		
		if (isUserExist != null) {
			return new ResponseEntity<>(environment.getProperty(PropertyKeyConstants.DUPLICATE_USER_ERROR_MSG),
					HttpStatus.BAD_REQUEST);
		} else {
			user.setEmail(emailPhoneDTO.getEmail());
			user.setPhoneNo(emailPhoneDTO.getPhoneNo());
			User registeredUser = userService.registerUser(user);
			
			if (registeredUser != null) {
				//String siteURL = commonsUtil.getSiteURL(request);
				//mailSenderUtil.sendWelcomeMail(registeredUser, siteURL);

				return new ResponseEntity<>(environment.getProperty(PropertyKeyConstants.REGISTRATION_SUCCESS_MSG),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(environment.getProperty(PropertyKeyConstants.REGISTRATION_FAILED_MSG),
						HttpStatus.BAD_REQUEST);
			}
		}
	}

}
