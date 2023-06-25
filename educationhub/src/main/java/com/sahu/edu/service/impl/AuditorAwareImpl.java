package com.sahu.edu.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import com.sahu.edu.constants.EducationHubConstants;
import com.sahu.edu.model.User;
import com.sahu.edu.service.UserService;
import com.sahu.edu.service.dto.CustomUserDetailsDTO;
import com.sahu.edu.util.security.SecurityUtil;

@Service
public class AuditorAwareImpl implements AuditorAware<Long> {

	@Autowired
	private UserService  userService;
	
	@Override
	public Optional<Long> getCurrentAuditor() {
		CustomUserDetailsDTO userDetailsDTO = SecurityUtil.getCurrentUser();
		if (userDetailsDTO != null) {
			return Optional.of(userDetailsDTO.getUserId());
		} else {
			Optional<User> user = userService.findByEmail(EducationHubConstants.EDH_SUPPORT_EMAIL);
			return Optional.of(user.get().getId());
		}
	}

}
