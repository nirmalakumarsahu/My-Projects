package com.sahu.dp.controller;

import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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

import com.sahu.dp.constants.CommonConstants;
import com.sahu.dp.constants.LVNConstants;
import com.sahu.dp.model.User;
import com.sahu.dp.service.UserService;
import com.sahu.dp.util.MailSenderUtil;
import com.sahu.dp.util.URLUtil;
import com.sahu.dp.util.UserUtil;

@Controller
public class LoginController {

	private Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserUtil userUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private Environment environment;

	@Autowired
	private MailSenderUtil mailSenderUtil;

	@GetMapping({ "/", "/login" })
	public String showLoginPage() {
		return LVNConstants.LOGIN_PAGE;
	}

	@GetMapping("/registration")
	public String showRegistrationPage() {
		return LVNConstants.REGISTRATION_PAGE;
	}

	@PostMapping("/registration")
	public String registrationProcess(RedirectAttributes redirectAttributes, @ModelAttribute("user") User user) {
		LOGGER.debug("Inside registrationProcess() method");
		LOGGER.info("User data - " + user.getEmail());
		if (user.getEmail() != null) {
			Optional<User> optionalUser = userService.findByEmail(user.getEmail());

			if (optionalUser.isPresent()) {
				redirectAttributes.addFlashAttribute(CommonConstants.ERROR,
						environment.getProperty("duplicate_user_error_msg"));
				return LVNConstants.REDIRECT_REGISTRATION;
			} else {
				Long registeredUserId = userUtil.registerUser(user);
				if (registeredUserId != null) {
					redirectAttributes.addFlashAttribute(CommonConstants.SUCCESS,
							environment.getProperty("registration_success_msg"));
				} else {
					redirectAttributes.addFlashAttribute(CommonConstants.ERROR,
							environment.getProperty("registration_failed_msg"));
				}
			}
		}

		return LVNConstants.REDIRECT_LOGIN_PAGE;
	}

	@GetMapping("/forget-password")
	public String showForgetPasswordPage() {
		return LVNConstants.FORGET_PASSWORD_PAGE;
	}

	@PostMapping("/forget-password")
	public String forgetPasswordProcess(RedirectAttributes redirectAttributes, @RequestParam("email") String email,
			HttpServletRequest request) {
		if (email != null) {
			Optional<User> optionalUser = userService.findByEmail(email);

			if (optionalUser.isPresent()) {
				User updatedUSer = userUtil.getTokenForReset(optionalUser.get());
				String restURL = URLUtil.getSiteURL(request);
				try {
					mailSenderUtil.sendMailForResetPassword(updatedUSer, restURL);
					redirectAttributes.addFlashAttribute(CommonConstants.SUCCESS,
							environment.getProperty("email_is_vaild_msg"));
				} catch (MessagingException e) {
					redirectAttributes.addFlashAttribute(CommonConstants.ERROR,
							environment.getProperty("email_is_not_vaild_msg"));
				}
			} else {
				redirectAttributes.addFlashAttribute(CommonConstants.ERROR,
						environment.getProperty("dont_have_account_msg"));
			}
		}

		return LVNConstants.REDIRECT_FORGET_PASSWORD;
	}

	@GetMapping("/reset-password")
	public String showResetPasswordPage(@RequestParam("token") String token, Map<String, Object> map,
			RedirectAttributes redirectAttributes) {
		if (token != null && token != "") {
			Optional<User> optionalUser = userService.findByResetPasswordToken(token);

			if (optionalUser.isPresent()) {
				map.put("token", token);
				return LVNConstants.RESET_PASSWORD_PAGE;
			}
		}

		redirectAttributes.addFlashAttribute(CommonConstants.ERROR, environment.getProperty("invalid_token_msg"));
		return LVNConstants.REDIRECT_FORGET_PASSWORD;
	}

	@PostMapping("/reset-password")
	public String resetPasswordProcess(@RequestParam("token") String token,
			@RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes) {
		if (token != null && token != "" && newPassword != null && newPassword != "") {
			Optional<User> optionalUser = userService.findByResetPasswordToken(token);

			if (optionalUser.isPresent()) {
				User user = userUtil.updatePassword(optionalUser.get(), newPassword);
				if (user != null)
					redirectAttributes.addFlashAttribute(CommonConstants.SUCCESS,
							environment.getProperty("is_password_changed_successfully"));
				return LVNConstants.REDIRECT_LOGIN_PAGE;
			}
		}

		redirectAttributes.addFlashAttribute(CommonConstants.ERROR, environment.getProperty("invalid_token_msg"));
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
