package com.sahu.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import com.sahu.model.User;
import com.sahu.service.UserService;
import com.sahu.service.dto.CustomUserDetailsDTO;
import com.sahu.util.security.SecurityUtil;

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
			Optional<User> user = userService.findByEmail("sus");
			return Optional.of(user.get().getId());
		}
	}

}
