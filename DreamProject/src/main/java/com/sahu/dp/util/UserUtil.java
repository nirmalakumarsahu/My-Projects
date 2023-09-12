package com.sahu.dp.util;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sahu.dp.constants.AuthenticationType;
import com.sahu.dp.constants.Status;
import com.sahu.dp.model.User;
import com.sahu.dp.service.UserService;

@Component
public class UserUtil {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Long registerUser(User user) {
		user.setUuid(UUID.randomUUID().toString());
		user.setStatus(Status.ACTIVE);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setAuthenticationType(AuthenticationType.DATABASE);
		user.setActive(true);
		return userService.save(user).getId();
	}

	public User getTokenForReset(User user) {
		String restPasswordToken = UUID.randomUUID().toString();
		user.setResetPasswordToken(restPasswordToken);
		return userService.save(user);
	}

	public User updatePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setResetPasswordToken(null);
		return userService.save(user);
	}

	public void oAuthPostLoginProcess(String email, String oAuth2ClientName) {
		Optional<User> optionalUser = userService.findByEmail(email);
		if (optionalUser.isEmpty()) {
			User newUser = new User();
			newUser.setUuid(UUID.randomUUID().toString());
			newUser.setStatus(Status.ACTIVE);
			newUser.setAuthenticationType(AuthenticationType.valueOf(oAuth2ClientName.toUpperCase()));
			newUser.setEmail(email);
			newUser.setActive(true);
			newUser.setRoles(null);
			userService.save(newUser);
		}
	}

}
