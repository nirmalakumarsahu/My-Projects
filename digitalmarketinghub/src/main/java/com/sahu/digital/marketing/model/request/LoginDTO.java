package com.sahu.digital.marketing.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginDTO {
	@JsonProperty("user_name_or_email")
	private String userNameOrEmail;
	@JsonProperty("password")
	private String password;
}
