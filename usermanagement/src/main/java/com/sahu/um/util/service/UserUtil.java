package com.sahu.um.util.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sahu.um.model.User;
import com.sahu.um.service.dto.UserDTO;

@Component
public class UserUtil {

	public static List<UserDTO> toUserDTo(List<User> userList) {
		return userList.stream().map(user -> toUserDTo(user)).collect(Collectors.toList());
	}

	public static UserDTO toUserDTo(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUuid(user.getUuid());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setStatus(user.getStatus());
		userDTO.setRoles(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
		return userDTO;
	}

}
