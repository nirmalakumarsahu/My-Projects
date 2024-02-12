package com.sahu.organizo.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sahu.organizo.constants.RoleConstants;
import com.sahu.organizo.constants.Status;
import com.sahu.organizo.dto.request.UserDTO;
import com.sahu.organizo.model.Role;
import com.sahu.organizo.model.User;
import com.sahu.organizo.repository.RoleRepository;
import com.sahu.organizo.repository.UserRepository;
import com.sahu.organizo.service.UserService;
import com.sahu.organizo.util.CommonsUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CommonsUtil commonsUtil;

	@Override
	public User findByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public User registerUser(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setUuid(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setOtp(commonsUtil.generateOTP());
		user.setDisplayName(userDTO.getFirstName()+" "+userDTO.getLastName());
		user.setActive(false);
		
		Optional<Role> role = roleRepository.findByName(RoleConstants.USER);
		if (role.isPresent()) {
			user.setRoles(Set.of(role.get()));
		}

		return userRepository.save(user);
	}

	@Override
	public User findByOtp(String otp) {
		Optional<User> optionalUser = userRepository.findByOtp(otp);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	@Override
	public Boolean activateUser(User user) {
		user.setOtp(null);
		user.setActive(true);
		user.setStatus(Status.ACTIVE);
		return userRepository.save(user) != null ? true : false;
	}

	@Override
	public String getResetPasswordCode(User user) {
		String otp = commonsUtil.generateOTP();
		user.setOtp(otp);
		user.setOtpValidedTime(commonsUtil.getCurrentDateTime());
		userRepository.save(user);
		return otp;
	}

	@Override
	public Boolean updatePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setOtp(null);
		user.setOtpValidedTime(null);
		return userRepository.save(user) != null ? true : false;
	}

	@Override
	public void removeUser(User user) {
		userRepository.delete(user);	
	}

}
