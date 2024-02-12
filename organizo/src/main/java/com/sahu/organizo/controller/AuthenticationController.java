package com.sahu.organizo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sahu.organizo.constants.LVNConstants;
import com.sahu.organizo.constants.OrganizoConstants;
import com.sahu.organizo.constants.PropertyKeyConstants;
import com.sahu.organizo.dto.request.UserDTO;
import com.sahu.organizo.model.User;
import com.sahu.organizo.service.UserService;
import com.sahu.organizo.util.CommonsUtil;
import com.sahu.organizo.util.MailSenderUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthenticationController {

	private Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private MailSenderUtil mailSenderUtil;
	
	@Autowired
	private CommonsUtil commonsUtil;
	
	@GetMapping("/login")
	public String showLoginPage() {
		return LVNConstants.LOGIN_PAGE;
	}

	@GetMapping("/registration")
	public String showRegistrationPage() {
		return LVNConstants.REGISTRATION_PAGE;
	}

	@PostMapping("/registration")
	public String registrationProcess(RedirectAttributes redirectAttributes, @ModelAttribute("user") UserDTO user,
			HttpServletRequest request) throws InterruptedException {
		LOGGER.debug("Inside registrationProcess() method");
		LOGGER.info("User EmailID - " + user.getEmail());

		User isUserExist = userService.findByEmail(user.getEmail());

		LOGGER.info("USER Present - " + isUserExist);
		if (isUserExist != null) {
			redirectAttributes.addFlashAttribute(OrganizoConstants.ERROR,
					environment.getProperty(PropertyKeyConstants.DUPLICATE_USER_ERROR_MSG));
			return LVNConstants.REDIRECT_REGISTRATION;
		} else {
			User registeredUser = userService.registerUser(user);

			if (registeredUser != null) {
				try {
					LOGGER.info("USER activation Code - " + registeredUser.getOtp());
					if (registeredUser.getEmail() != null) {
						mailSenderUtil.sendWelcomeAndActiveAccountMail(registeredUser, commonsUtil.getSiteURL(request),
								commonsUtil.getActivationURL(request, registeredUser.getOtp()));
					}

					redirectAttributes.addFlashAttribute(OrganizoConstants.SUCCESS,
						environment.getProperty(PropertyKeyConstants.REGISTRATION_SUCCESS_MSG));
				} catch (MessagingException e) {
					userService.removeUser(registeredUser);
					redirectAttributes.addFlashAttribute(OrganizoConstants.ERROR,
							environment.getProperty(PropertyKeyConstants.UNABLE_TO_SENT_ACTIVATION_MAIL));
				}
			} else {
				redirectAttributes.addFlashAttribute(OrganizoConstants.ERROR,
						environment.getProperty(PropertyKeyConstants.REGISTRATION_FAILED_MSG));
			}	
		}
		
		return LVNConstants.REDIRECT_LOGIN;
	}

	@GetMapping("/activation")
	public String activattionOfAcoount(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
		LOGGER.debug("Inside activattionOfAcoount() method");
		
		if (code != null && code != "") {
			User isUserExist = userService.findByOtp(code);

			if (isUserExist != null) {
				Boolean isUserActivated = userService.activateUser(isUserExist);
				
				if (isUserActivated)
					redirectAttributes.addFlashAttribute(OrganizoConstants.SUCCESS,
							environment.getProperty(PropertyKeyConstants.ACCOUNT_ACTIVATED_SUCCESSFULLY));
			}
			else {
				redirectAttributes.addFlashAttribute(OrganizoConstants.ERROR,
						environment.getProperty(PropertyKeyConstants.INVALID_ACTIVATION_CODE_MSG));
			}
		} else {
			redirectAttributes.addFlashAttribute(OrganizoConstants.ERROR,
					environment.getProperty(PropertyKeyConstants.ACTIVATION_CODE_NOT_NULL));
		}

		return LVNConstants.REDIRECT_LOGIN;
	}

	@GetMapping("/forget-password")
	public String showForgetPasswordPage() {
		return LVNConstants.FORGET_PASSWORD_PAGE;
	}

	@PostMapping("/forget-password")
	public String forgetPasswordProcess(RedirectAttributes redirectAttributes, @RequestParam("email") String email,
			HttpServletRequest request) {
		LOGGER.debug("Inside forgetPasswordProcess() method");
		
		if (email != null) {
			User isUserExist = userService.findByEmail(email);

			if (isUserExist != null) {
				try {
					String restPasswordCode = userService.getResetPasswordCode(isUserExist);
					mailSenderUtil.sendMailForResetPassword(isUserExist, commonsUtil.getSiteURL(request),
							commonsUtil.getPasswordRestURL(request, restPasswordCode));

					redirectAttributes.addFlashAttribute(OrganizoConstants.SUCCESS,
							environment.getProperty(PropertyKeyConstants.FORGET_PASSWORD_MAIL_SENT));
				} catch (MessagingException e) {
					redirectAttributes.addFlashAttribute(OrganizoConstants.ERROR,
							environment.getProperty(PropertyKeyConstants.UNABLE_TO_SENT_RESET_MAIL));
				}
			} else {
				redirectAttributes.addFlashAttribute(OrganizoConstants.ERROR,
					environment.getProperty(PropertyKeyConstants.DONT_HAVE_ACCOUNT_MSG));
			}
		}

		return LVNConstants.REDIRECT_FORGET_PASSWORD;
	}

	@GetMapping("/reset-password")
	public String showResetPasswordPage(@RequestParam("code") String code, Map<String, Object> map,
			RedirectAttributes redirectAttributes) {
		LOGGER.debug("Inside showResetPasswordPage() method");
		
		if (code != null && code != "") {
			User isUserExist = userService.findByOtp(code);

			if (isUserExist != null) {
				map.put("code", code);
				return LVNConstants.RESET_PASSWORD_PAGE;
			}
		}
		
		redirectAttributes.addFlashAttribute(OrganizoConstants.ERROR,
					environment.getProperty(PropertyKeyConstants.INVALID_RESET_PWD_CODE_MSG));
		return LVNConstants.REDIRECT_FORGET_PASSWORD;
	}

	@PostMapping("/reset-password")
	public String resetPasswordProcess(@RequestParam("code") String code,
			@RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes) {
		LOGGER.debug("Inside resetPasswordProcess() method");
		
		if (code != null && code != "" && newPassword != null && newPassword != "") {
			User isUserExist = userService.findByOtp(code);

			if (isUserExist != null) {
				Boolean passwordUpdated = userService.updatePassword(isUserExist, newPassword);
				
				if (passwordUpdated) {
					redirectAttributes.addFlashAttribute(OrganizoConstants.SUCCESS,
							environment.getProperty(PropertyKeyConstants.PASSWORD_CHANGED_SUCCESSFULLY));
					return LVNConstants.REDIRECT_LOGIN;
				}
			}
		}

		redirectAttributes.addFlashAttribute(OrganizoConstants.ERROR,
				environment.getProperty(PropertyKeyConstants.INVALID_RESET_PWD_CODE_MSG));
		return LVNConstants.REDIRECT_FORGET_PASSWORD;
	}

	@GetMapping("/change-password")
	public String showChangePasswordPage() {
		return LVNConstants.CHANGE_PASSWORD_PAGE;
	}

	@PostMapping("/change-password")
	public String ChangePasswordProcess() {
		return null;
	}

}
