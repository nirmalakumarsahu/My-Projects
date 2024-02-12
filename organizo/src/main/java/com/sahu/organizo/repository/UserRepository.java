package com.sahu.organizo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sahu.organizo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUserName(String userName);
	
	Optional<User> findByUserNameOrEmail(String userName, String email);

	Optional<User> findByOtp(String otp);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByUserName(String email);
	
}
