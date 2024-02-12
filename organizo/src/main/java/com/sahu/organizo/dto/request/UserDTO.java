package com.sahu.organizo.dto.request;

import lombok.Data;

@Data
public class UserDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
