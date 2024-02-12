package com.sahu.digital.marketing.service.impl;

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

import com.sahu.digital.marketing.model.Permission;
import com.sahu.digital.marketing.model.Role;
import com.sahu.digital.marketing.model.User;
import com.sahu.digital.marketing.service.UserService;
import com.sahu.digital.marketing.service.dto.CustomUserDetailsDTO;
import com.sahu.digital.marketing.service.dto.EmailPhoneDTO;
import com.sahu.digital.marketing.util.CommonsUtil;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CommonsUtil commonsUtil;

	@Override
	public CustomUserDetailsDTO loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Inside loadUserByUsername() method");
		EmailPhoneDTO emailPhoneDTO = commonsUtil.checkEmailOrPhoneNo(username);
		User user = userService.findByEmailOrPhoneNo(emailPhoneDTO.getEmail(), emailPhoneDTO.getPhoneNo());
		LOGGER.info("User - " + user);

		if (user == null) {
			throw new UsernameNotFoundException("User is not exist");
		} else if (!user.getActive()) {
			throw new UsernameNotFoundException("User is not active, please contact admin!");
		} else {
			Long loggedInUserId = user.getId();

			List<GrantedAuthority> authorities = user.getRoles().stream()
					.flatMap(role -> role.getPermissions().stream()
							.map(permission -> new SimpleGrantedAuthority(permission.getName())))
					.collect(Collectors.toList());

			List<String> userRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
			List<String> userPermissions = user.getRoles().stream()
					.flatMap(role -> role.getPermissions().stream().map(Permission::getPermissionKey))
					.collect(Collectors.toList());

			LOGGER.info("User object created");
			return new CustomUserDetailsDTO(username, user.getPassword(), authorities, loggedInUserId, user.getUuid(),
					user.getFirstName(), user.getEmail(), user.getPhoneNo(), userRoles, userPermissions);
		}
	}

}
