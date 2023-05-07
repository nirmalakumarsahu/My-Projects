package com.sahu.um.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sahu.um.constants.RoleConstants;
import com.sahu.um.model.User;
import com.sahu.um.repository.UserRepository;
import com.sahu.um.service.dto.CustomUserDetailsDTO;

public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public CustomUserDetailsDTO loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Inside loadUserByUsername() method");
		User user = userRepository.findByEmail(username);
		LOGGER.info("User - " + user);
		
		if (user == null) {
			throw new UsernameNotFoundException("User is not exist");
		} else if (!user.getActive()) {
			throw new UsernameNotFoundException("User is not active, please contact admin!");
		} else {
			Long loggedInUserId = user.getId();
			List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(RoleConstants.ROLE_USER));
			List<String> userRoles = new ArrayList<>();
			List<String> userPermissions = new ArrayList<>();
			
			//Here we have to write the logic to collect Roles and permission
			
			return new CustomUserDetailsDTO(user.getEmail(), user.getPassword(), authorities, loggedInUserId, userRoles, userPermissions);
		}
	}

}
