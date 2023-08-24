package com.sahu.request.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {
	@JsonProperty("user_name")
	private String userName;
	@JsonProperty("password")
	private String password;
}
