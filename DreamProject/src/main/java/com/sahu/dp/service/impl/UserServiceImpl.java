package com.sahu.dp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahu.dp.constants.AuthenticationType;
import com.sahu.dp.model.User;
import com.sahu.dp.repository.UserRepository;
import com.sahu.dp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByResetPasswordToken(String resetPasswordToken) {
		return userRepository.findByResetPasswordToken(resetPasswordToken);
	}

	@Override
	public void updateAuthenticationType(Long userId, AuthenticationType authenticationType) {
		userRepository.updateAuthenticationType(userId, authenticationType);
	}

}
