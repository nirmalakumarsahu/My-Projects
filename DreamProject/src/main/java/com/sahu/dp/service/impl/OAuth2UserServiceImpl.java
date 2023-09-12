package com.sahu.dp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sahu.dp.model.Permission;
import com.sahu.dp.model.Role;
import com.sahu.dp.model.User;
import com.sahu.dp.repository.PermissionRepository;
import com.sahu.dp.repository.RoleRepository;
import com.sahu.dp.repository.UserRepository;
import com.sahu.dp.service.dto.CustomOAuth2UserDTO;

@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

	private Logger LOGGER = LoggerFactory.getLogger(OAuth2UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		LOGGER.debug("Inside loadUser() method");
		String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User oAuth2User =  super.loadUser(userRequest);
        LOGGER.info("User Name - "+oAuth2User.getName());
       
        String email = oAuth2User.getAttribute("email");
        Optional<User> user = userRepository.findByEmail(email);
		LOGGER.info("User - " + user);
		
		if (user.get() == null) {
			throw new UsernameNotFoundException("User is not exist");
		} else if (!user.get().getActive()) {
			throw new UsernameNotFoundException("User is not active, please contact admin!");
		} else {
			Long loggedInUserId = user.get().getId();
			
			// Here we have to write the logic to collect Roles and permission
			List<Role> roleList = roleRepository.getRolesOfUserByUserId(loggedInUserId);
			List<Permission> permissionList = permissionRepository.getAllPermissionsOfUserByUserId(loggedInUserId);

			List<String> userRoles = roleList.stream().map(Role::getName).collect(Collectors.toList());
			List<String> userPermissions = permissionList.stream().map(Permission::getName)
					.collect(Collectors.toList());

			LOGGER.info("User object created");
			
			return new CustomOAuth2UserDTO(clientName, oAuth2User, loggedInUserId, userRoles, userPermissions);
		}
	}
	
}
