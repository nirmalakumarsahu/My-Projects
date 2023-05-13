package com.sahu.um.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sahu.um.constants.RoleConstants;
import com.sahu.um.model.Permission;
import com.sahu.um.model.Role;
import com.sahu.um.model.User;
import com.sahu.um.repository.PermissionRepository;
import com.sahu.um.repository.RoleRepository;
import com.sahu.um.repository.UserRepository;
import com.sahu.um.service.dto.CustomUserDetailsDTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	public CustomUserDetailsDTO loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Inside loadUserByUsername() method");
		Optional<User> user = userRepository.findByEmail(username);
		LOGGER.info("User - " + user);
		
		if (user.get() == null) {
			throw new UsernameNotFoundException("User is not exist");
		} else if (!user.get().getActive()) {
			throw new UsernameNotFoundException("User is not active, please contact admin!");
		} else {
			Long loggedInUserId = user.get().getId();
			List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(RoleConstants.ROLE_USER));
			
			//Here we have to write the logic to collect Roles and permission
			List<Role> roleList = roleRepository.getRolesOfUserByUserId(loggedInUserId);
			List<Permission> permissionList = permissionRepository.getAllPermissionsOfUserByUserId(loggedInUserId);
			
			List<String> userRoles = roleList.stream().map(Role::getName).collect(Collectors.toList());
			List<String> userPermissions = permissionList.stream().map(Permission::getName).collect(Collectors.toList());
			
			return new CustomUserDetailsDTO(user.get().getEmail(), user.get().getPassword(), authorities, loggedInUserId, userRoles, userPermissions);
		}
	}

}
