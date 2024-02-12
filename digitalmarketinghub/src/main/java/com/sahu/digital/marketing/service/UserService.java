package com.sahu.digital.marketing.service;

import com.sahu.digital.marketing.dto.request.UserDTO;
import com.sahu.digital.marketing.model.User;

public interface UserService {

	public User findByEmail(String email);
	
	public User findByEmailOrPhoneNo(String email, Long phoneNo);
	
	public User registerUser(UserDTO user);
	
	public User findByActivationCode(String activationCode);
	
	public Boolean activateUser(User user);
	
	public String getTokenForReset(User user);

	public User findByResetPasswordToken(String resetPasswordToken);

	public Boolean updatePassword(User user, String newPassword);
}
