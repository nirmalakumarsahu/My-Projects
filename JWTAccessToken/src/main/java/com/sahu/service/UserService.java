package com.sahu.service;

import java.util.Optional;

import com.sahu.model.User;

public interface UserService {

	public Optional<User> findByEmail(String email);
	
	public Long registerUser(User user);
}
