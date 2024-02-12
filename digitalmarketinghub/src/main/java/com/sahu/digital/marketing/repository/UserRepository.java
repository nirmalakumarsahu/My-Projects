package com.sahu.digital.marketing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sahu.digital.marketing.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByEmailOrPhoneNo(String email, Long phoneNo);

	public Optional<User> findByResetPasswordToken(String resetPasswordToken);
	
	public Optional<User> findByActivationCode(String activationCode);

}
