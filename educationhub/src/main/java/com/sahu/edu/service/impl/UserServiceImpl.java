package com.sahu.edu.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahu.edu.model.User;
import com.sahu.edu.repository.UserRepository;
import com.sahu.edu.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Optional<User> findByEmail(String email) {
		return Optional.empty();
	}

}
