package com.sahu.um.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import com.sahu.um.constants.UserManagementConstants;
import com.sahu.um.model.User;
import com.sahu.um.service.UserService;
import com.sahu.um.service.dto.CustomUserDetailsDTO;
import com.sahu.um.util.security.SecurityUtil;

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
			Optional<User> user = userService.findByEmail(UserManagementConstants.UM_SUPPORT_EMAIL);
			return Optional.of(user.get().getId());
		}
	}

}
