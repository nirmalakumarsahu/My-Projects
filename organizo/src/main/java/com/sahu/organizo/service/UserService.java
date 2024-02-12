package com.sahu.organizo.service;

import com.sahu.organizo.dto.request.UserDTO;
import com.sahu.organizo.model.User;

public interface UserService {

	User findByEmail(String email);
	
	User registerUser(UserDTO user);
	
	User findByOtp(String otp);
	
	Boolean activateUser(User user);
	
	String getResetPasswordCode(User user);

	Boolean updatePassword(User user, String newPassword);
	
	void removeUser(User user); 
	
}
