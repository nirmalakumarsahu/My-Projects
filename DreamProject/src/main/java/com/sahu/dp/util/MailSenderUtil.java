package com.sahu.dp.util;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.sahu.dp.constants.CommonConstants;
import com.sahu.dp.model.User;

@Component
public class MailSenderUtil {

	private Logger LOGGER = LoggerFactory.getLogger(MailSenderUtil.class);

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	private Environment environment;

	public String sendMailForResetPassword(User user, String restURL) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage(); // Empty email message
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
		messageHelper.setFrom(fromEmail);
		messageHelper.setTo(user.getEmail());
		messageHelper.setSubject(environment.getProperty("reset_password_subject"));
		messageHelper.setSentDate(new Date());

		String body = environment.getProperty("reset_password_body");
		body = String.format(body, user.getFirstName() + " " + user.getLastName(),
				restURL + CommonConstants.RESET_PWD_TOKEN_ATR + user.getResetPasswordToken());
		LOGGER.info("BODY - " + body);

		messageHelper.setText(body, true);
		mailSender.send(mimeMessage);
		return "Mail sent";
	}

}
