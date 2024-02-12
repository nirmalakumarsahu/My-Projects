package com.sahu.digital.marketing.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sahu.digital.marketing.constants.RoleConstants;
import com.sahu.digital.marketing.constants.Status;
import com.sahu.digital.marketing.dto.request.UserDTO;
import com.sahu.digital.marketing.model.Role;
import com.sahu.digital.marketing.model.User;
import com.sahu.digital.marketing.repository.RoleRepository;
import com.sahu.digital.marketing.repository.UserRepository;
import com.sahu.digital.marketing.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}
	
	@Override
	public User findByEmailOrPhoneNo(String email, Long phoneNo) {
		Optional<User> optionalUser = userRepository.findByEmailOrPhoneNo(email, phoneNo);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public User registerUser(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setUuid(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		Date currentDate = new Date();
		user.setCreatedAt(currentDate);
		user.setUpdatedAt(currentDate);
		
		String activationCode = UUID.randomUUID().toString();
		user.setActivationCode(activationCode);
		
		Optional<Role> role = roleRepository.findByRoleKey(RoleConstants.USER);
		if (role.isPresent()) {
			user.setRoles(List.of(role.get()));
		}

		return userRepository.save(user);
	}

	
	@Override
	public User findByActivationCode(String activationCode) {
		Optional<User> optionalUser = userRepository.findByActivationCode(activationCode);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public Boolean activateUser(User user) {
		user.setActivationCode(null);
		user.setActive(true);
		user.setStatus(Status.ACTIVE);
		return userRepository.save(user) != null ? true : false;
	}

	@Override
	public String getTokenForReset(User user) {
		String restPasswordToken = UUID.randomUUID().toString();
		user.setResetPasswordToken(restPasswordToken);
		userRepository.save(user);
		return restPasswordToken;
	}

	@Override
	public User findByResetPasswordToken(String resetPasswordToken) {
		Optional<User> optionalUser = userRepository.findByResetPasswordToken(resetPasswordToken);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public Boolean updatePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setResetPasswordToken(null);
		return userRepository.save(user) != null ? true : false;
	}

}
