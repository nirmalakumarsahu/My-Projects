package com.sahu.organizo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sahu.organizo.model.Role;
import com.sahu.organizo.model.User;
import com.sahu.organizo.repository.UserRepository;
import com.sahu.organizo.security.dto.CustomUserDetailsDTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public CustomUserDetailsDTO loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Inside loadUserByUsername() method");
		LOGGER.info("username - " + username);
		User user = userRepository.findByUserNameOrEmail(username, username).orElseThrow(()->
				new UsernameNotFoundException("User is not exist"));
		LOGGER.info("User - " + user);

		if (!user.getActive()) {
			LOGGER.debug("User is not active");
			throw new UsernameNotFoundException("User is not active, please contact admin!");
		} else {
			Long loggedInUserId = user.getId();
			
			List<GrantedAuthority> authorities = user.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getName()))
					.collect(Collectors.toList());

			List<String> userRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

			LOGGER.debug("User object created");
			return new CustomUserDetailsDTO(username, user.getPassword(), authorities, loggedInUserId, user.getUuid(),
					user.getDisplayName(), user.getEmail(), userRoles);
		}
	}

}
