package com.sahu.dp.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sahu.dp.service.dto.CustomOAuth2UserDTO;
import com.sahu.dp.util.UserUtil;

@Component
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger LOGGER = LoggerFactory.getLogger(OAuthLoginSuccessHandler.class);

	@Autowired
	private UserUtil userUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		LOGGER.debug("Inside onAuthenticationSuccess() method");
		CustomOAuth2UserDTO oauth2User = (CustomOAuth2UserDTO) authentication.getPrincipal();
		String oauth2ClientName = oauth2User.getOauth2ClientName();
		String email = oauth2User.getEmail();
		
		LOGGER.info("Email - " + email + ", oauth2ClientName - " + oauth2ClientName);
		userUtil.oAuthPostLoginProcess(email, oauth2ClientName);
		
		response.sendRedirect("/client/user/dashboard");
	}

}
