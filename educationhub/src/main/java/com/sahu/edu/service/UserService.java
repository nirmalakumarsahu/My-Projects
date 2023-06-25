package com.sahu.edu.service;

import java.util.Optional;

import com.sahu.edu.model.User;

public interface UserService {

	public Optional<User> findByEmail(String email);
}
