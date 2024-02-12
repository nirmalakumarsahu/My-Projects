package com.sahu.organizo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import com.sahu.organizo.security.SecurityUtil;
import com.sahu.organizo.security.dto.CustomUserDetailsDTO;

@Service
public class AuditorAwareImpl implements AuditorAware<Long> {
	
	@Override
	public Optional<Long> getCurrentAuditor() {
		CustomUserDetailsDTO userDetailsDTO = SecurityUtil.getCurrentUser();
		if (userDetailsDTO != null) {
			return Optional.of(userDetailsDTO.getUserId());
		} else {
			return Optional.of(2l);
		}
	}

}
