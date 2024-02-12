package com.sahu.digital.marketing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sahu.digital.marketing.constants.DigitalMarketingHubConstants;
import com.sahu.digital.marketing.service.dto.EmailPhoneDTO;

@Component
public class CommonsUtil {

	public String getSiteURL(HttpServletRequest request) {
		return ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toString();
	}

	public String getPasswordRestURL(HttpServletRequest request, String resetPasswordToken) {
		return getSiteURL(request) + DigitalMarketingHubConstants.RESET_PWD_TOKEN_ATR + resetPasswordToken;
	}

	public String getActivationURL(HttpServletRequest request, String activationCode) {
		return getSiteURL(request) + DigitalMarketingHubConstants.ACTIVATION_CODE_ATR + activationCode;
	}

	public EmailPhoneDTO checkEmailOrPhoneNo(String username) {
		Matcher emailMatcher = Pattern.compile(DigitalMarketingHubConstants.EMAIL_REGEXP).matcher(username);
		Matcher phoneNoMatcher = Pattern.compile(DigitalMarketingHubConstants.PHONE_REGEXP).matcher(username);

		EmailPhoneDTO emailPhoneDTO = new EmailPhoneDTO();
		if (emailMatcher.matches())
			emailPhoneDTO.setEmail(username);

		if (phoneNoMatcher.matches())
			emailPhoneDTO.setPhoneNo(Long.valueOf(username));

		return emailPhoneDTO;
	}

}
