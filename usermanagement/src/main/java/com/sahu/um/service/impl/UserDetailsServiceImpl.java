package com.sahu.um.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sahu.um.service.dto.CustomUserDetailsDTO;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public CustomUserDetailsDTO loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

}
