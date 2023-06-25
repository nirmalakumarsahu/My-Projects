package com.sahu.edu.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sahu.edu.constants.RoleConstants;
import com.sahu.edu.constants.Status;
import com.sahu.edu.model.User;
import com.sahu.edu.repository.RoleRepository;
import com.sahu.edu.repository.UserRepository;
import com.sahu.edu.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Long registerUser(User user) {
		user.setUuid(UUID.randomUUID().toString());
		user.setStatus(Status.ACTIVE.getValue());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(true);
		user.setRoles(List.of(roleRepository.findByName(RoleConstants.STUDENT)));
		return userRepository.save(user).getId();
	}
	
}
