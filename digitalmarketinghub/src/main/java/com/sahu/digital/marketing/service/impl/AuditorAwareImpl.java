package com.sahu.digital.marketing.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import com.sahu.digital.marketing.constants.DigitalMarketingHubConstants;
import com.sahu.digital.marketing.model.User;
import com.sahu.digital.marketing.security.SecurityUtil;
import com.sahu.digital.marketing.service.UserService;
import com.sahu.digital.marketing.service.dto.CustomUserDetailsDTO;

@Service
public class AuditorAwareImpl implements AuditorAware<User> {

	@Autowired
	private UserService userService;

	@Override
	public Optional<User> getCurrentAuditor() {
		CustomUserDetailsDTO userDetailsDTO = SecurityUtil.getCurrentUser();
		String email = userDetailsDTO != null ? userDetailsDTO.getEmail() : DigitalMarketingHubConstants.SUPPORT_MAIL;
		User user = userService.findByEmail(email);
		return Optional.of(user);
	}

}
