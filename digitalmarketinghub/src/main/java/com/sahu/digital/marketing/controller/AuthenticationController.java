package com.sahu.digital.marketing.controller;

import java.util.Map;

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

import com.sahu.digital.marketing.constants.DigitalMarketingHubConstants;
import com.sahu.digital.marketing.constants.LVNConstants;
import com.sahu.digital.marketing.constants.PropertyKeyConstants;
import com.sahu.digital.marketing.dto.request.UserDTO;
import com.sahu.digital.marketing.model.User;
import com.sahu.digital.marketing.service.UserService;
import com.sahu.digital.marketing.service.dto.EmailPhoneDTO;
import com.sahu.digital.marketing.util.CommonsUtil;
import com.sahu.digital.marketing.util.MailSenderUtil;

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
		LOGGER.info("User data - " + user.getEmailorPhoneNo());

		EmailPhoneDTO emailPhoneDTO = commonsUtil.checkEmailOrPhoneNo(user.getEmailorPhoneNo());
		User isUserExist = userService.findByEmailOrPhoneNo(emailPhoneDTO.getEmail(), emailPhoneDTO.getPhoneNo());

		LOGGER.info("USER Present - " + isUserExist);
		if (isUserExist != null) {
			redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.ERROR,
					environment.getProperty(PropertyKeyConstants.DUPLICATE_USER_ERROR_MSG));
			return LVNConstants.REDIRECT_REGISTRATION;
		} else {
			user.setEmail(emailPhoneDTO.getEmail());
			user.setPhoneNo(emailPhoneDTO.getPhoneNo());
			User registeredUser = userService.registerUser(user);

			if (registeredUser != null) {
				try {
					LOGGER.info("USER activation Code - " + registeredUser.getActivationCode());
					if (registeredUser.getEmail() != null) {
						mailSenderUtil.sendWelcomeAndActiveAccountMail(registeredUser, commonsUtil.getSiteURL(request),
								commonsUtil.getActivationURL(request, registeredUser.getActivationCode()));
					}

					redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.SUCCESS,
							environment.getProperty(PropertyKeyConstants.REGISTRATION_SUCCESS_MSG));
				} catch (MessagingException e) {
					redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.ERROR,
							environment.getProperty(PropertyKeyConstants.EMAIL_IS_NOT_VAILD_MSG));
				}
			} else {
				redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.ERROR,
						environment.getProperty(PropertyKeyConstants.REGISTRATION_FAILED_MSG));
			}
		}

		return LVNConstants.REDIRECT_LOGIN;
	}

	@GetMapping("/activation")
	public String activattionOfAcoount(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
		LOGGER.debug("Inside activattionOfAcoount() method");
		if (code != null && code != "") {
			User isUserExist = userService.findByActivationCode(code);

			if (isUserExist != null) {
				Boolean passwordUpdated = userService.activateUser(isUserExist);
				if (passwordUpdated)
					redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.SUCCESS,
							environment.getProperty(PropertyKeyConstants.ACCOUNT_ACTIVATED_SUCCESSFULLY));
			} else {
				redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.ERROR,
						environment.getProperty(PropertyKeyConstants.INVALID_ACTIVATION_CODE_MSG));
			}
		} else {
			redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.ERROR,
					environment.getProperty(PropertyKeyConstants.INVALID_ACTIVATION_CODE_MSG));
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
		if (email != null) {
			User isUserExist = userService.findByEmail(email);

			if (isUserExist != null) {
				try {
					String restPasswordToken = userService.getTokenForReset(isUserExist);
					mailSenderUtil.sendMailForResetPassword(isUserExist, commonsUtil.getSiteURL(request),
							commonsUtil.getPasswordRestURL(request, restPasswordToken));

					redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.SUCCESS,
							environment.getProperty(PropertyKeyConstants.EMAIL_IS_VAILD_MSG));
				} catch (MessagingException e) {
					redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.ERROR,
							environment.getProperty(PropertyKeyConstants.EMAIL_IS_NOT_VAILD_MSG));
				}
			} else {
				redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.ERROR,
						environment.getProperty(PropertyKeyConstants.DONT_HAVE_ACCOUNT_MSG));
			}
		}

		return LVNConstants.REDIRECT_FORGET_PASSWORD;
	}

	@GetMapping("/reset-password")
	public String showResetPasswordPage(@RequestParam("token") String token, Map<String, Object> map,
			RedirectAttributes redirectAttributes) {
		if (token != null && token != "") {
			User isUserExist = userService.findByResetPasswordToken(token);

			if (isUserExist != null) {
				map.put("token", token);
				return LVNConstants.RESET_PASSWORD_PAGE;
			}
		}

		redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.ERROR,
				environment.getProperty(PropertyKeyConstants.INVALID_TOKEN_MSG));
		return LVNConstants.REDIRECT_FORGET_PASSWORD;
	}

	@PostMapping("/reset-password")
	public String resetPasswordProcess(@RequestParam("token") String token,
			@RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes) {
		if (token != null && token != "" && newPassword != null && newPassword != "") {
			User isUserExist = userService.findByResetPasswordToken(token);

			if (isUserExist != null) {
				Boolean passwordUpdated = userService.updatePassword(isUserExist, newPassword);
				if (passwordUpdated)
					redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.SUCCESS,
							environment.getProperty(PropertyKeyConstants.PASSWORD_CHANGED_SUCCESSFULLY));
				return LVNConstants.REDIRECT_LOGIN;
			}
		}

		redirectAttributes.addFlashAttribute(DigitalMarketingHubConstants.ERROR,
				environment.getProperty(PropertyKeyConstants.INVALID_TOKEN_MSG));
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
