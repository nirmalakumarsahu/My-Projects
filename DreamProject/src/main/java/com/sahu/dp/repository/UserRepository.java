package com.sahu.dp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sahu.dp.constants.AuthenticationType;
import com.sahu.dp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByEmail(String email);

	public Optional<User> findByResetPasswordToken(String resetPasswordToken);

	@Query(nativeQuery = true, value = "UPDATE user as user SET user.auth_type =:authenticationType WHERE user.id =:userId")
	public void updateAuthenticationType(Long userId, AuthenticationType authenticationType);

}
