package com.sahu.um.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private Long id;
	private String uuid;
	private String firstName;
	private String lastName;
	private String email;
}
