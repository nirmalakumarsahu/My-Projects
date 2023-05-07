package com.sahu.um.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahu.um.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
}
