package com.sahu.organizo.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.sahu.organizo.constants.MailConstants;
import com.sahu.organizo.controller.AuthenticationController;
import com.sahu.organizo.model.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailSenderUtil {
	
	private Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Autowired
	private Environment environment;

	public void sendWelcomeAndActiveAccountMail(User user, String siteURL, String activationURL) throws MessagingException {
		LOGGER.debug("Inside sendWelcomeAndActiveAccountMail() method");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
		messageHelper.setFrom(fromEmail);
		messageHelper.setTo(user.getEmail());
		messageHelper.setSubject(environment.getProperty(MailConstants.WELCOME_TO_WEBSITE_SUBJECT));
		messageHelper.setSentDate(new Date());

		Map<String, Object> replacements = new HashMap<>();
		replacements.put(MailConstants.SITE_URL, siteURL);
		replacements.put(MailConstants.USER_NAME, user.getFirstName() + " " + user.getLastName());
		replacements.put(MailConstants.ACTIVATION_URL, activationURL);

		Context context = new Context();
		context.setVariables(replacements);
		messageHelper.setText(templateEngine.process(MailConstants.WELCOM_MAIL_TEMPL, context), true);

		mailSender.send(mimeMessage);
		LOGGER.debug("Email send");
	}

	public String sendMailForResetPassword(User user, String siteURL, String resetPasswordURL)
			throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
		messageHelper.setFrom(fromEmail);
		messageHelper.setTo(user.getEmail());
		messageHelper.setSubject(environment.getProperty(MailConstants.RESET_PASSWORD_SUBJECT));
		messageHelper.setSentDate(new Date());

		Map<String, Object> replacements = new HashMap<>();
		replacements.put(MailConstants.SITE_URL, siteURL);
		replacements.put(MailConstants.USER_NAME, user.getFirstName() + " " + user.getLastName());
		replacements.put(MailConstants.RESET_PSW_URL, resetPasswordURL);

		Context context = new Context();
		context.setVariables(replacements);
		messageHelper.setText(templateEngine.process(MailConstants.RESET_PWD_MAIL_TEMPL, context), true);

		mailSender.send(mimeMessage);
		return "Mail sent";
	}

}
