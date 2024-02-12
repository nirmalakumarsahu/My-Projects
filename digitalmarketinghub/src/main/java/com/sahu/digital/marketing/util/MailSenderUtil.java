package com.sahu.digital.marketing.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.sahu.digital.marketing.constants.MailConstants;
import com.sahu.digital.marketing.constants.PropertyKeyConstants;
import com.sahu.digital.marketing.model.User;

@Component
public class MailSenderUtil {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	private Environment environment;

	@Autowired
	private SpringTemplateEngine templateEngine;

	public String sendWelcomeAndActiveAccountMail(User user, String siteURL, String activationURL) throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
		messageHelper.setFrom(fromEmail);
		messageHelper.setTo(user.getEmail());
		messageHelper.setSubject(environment.getProperty(PropertyKeyConstants.WELCOME_TO_WEBSITE_SUBJECT));
		messageHelper.setSentDate(new Date());

		Map<String, Object> replacements = new HashMap<>();
		replacements.put(MailConstants.SITE_URL, siteURL);
		replacements.put(MailConstants.USER_NAME, user.getFirstName() + " " + user.getLastName());
		replacements.put(MailConstants.ACTIVATION_URL, activationURL);

		Context context = new Context();
		context.setVariables(replacements);
		messageHelper.setText(templateEngine.process(MailConstants.WELCOM_MAIL_TEMPL, context), true);

		mailSender.send(mimeMessage);
		return "Mail sent";
	}

	public String sendMailForResetPassword(User user, String siteURL, String passwordResetURL)
			throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
				MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
		messageHelper.setFrom(fromEmail);
		messageHelper.setTo(user.getEmail());
		messageHelper.setSubject(environment.getProperty(PropertyKeyConstants.RESET_PASSWORD_SUBJECT));
		messageHelper.setSentDate(new Date());

		Map<String, Object> replacements = new HashMap<>();
		replacements.put(MailConstants.SITE_URL, siteURL);
		replacements.put(MailConstants.USER_NAME, user.getFirstName() + " " + user.getLastName());
		replacements.put(MailConstants.REST_PSW_URL, passwordResetURL);

		Context context = new Context();
		context.setVariables(replacements);
		messageHelper.setText(templateEngine.process(MailConstants.RESET_PWD_MAIL_TEMPL, context), true);

		mailSender.send(mimeMessage);
		return "Mail sent";
	}

}
