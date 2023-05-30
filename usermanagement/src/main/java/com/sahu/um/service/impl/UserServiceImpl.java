package com.sahu.um.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sahu.um.constants.Status;
import com.sahu.um.model.User;
import com.sahu.um.repository.UserRepository;
import com.sahu.um.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
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
		return userRepository.save(user).getId();
	}

	@Override
	public DataTablesOutput<User> findAll(DataTablesInput dataTablesInput, Specification<User> specification) {
		return userRepository.findAll(dataTablesInput, specification);
	}
	
}
