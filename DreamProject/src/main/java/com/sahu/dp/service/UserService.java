package com.sahu.dp.service;

import java.util.Optional;

import com.sahu.dp.constants.AuthenticationType;
import com.sahu.dp.model.User;

public interface UserService {

	public Optional<User> findByEmail(String email);

	public User save(User user);
	
	public Optional<User> findByResetPasswordToken(String resetPasswordToken);

	public void updateAuthenticationType(Long userId, AuthenticationType authenticationType);
}
