package com.sahu.response.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JWTAuthResponseDTO {
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("token_type")
	private String tokenType = "Bearer";
	@JsonProperty("expiration_date")
	private Date expirationDate;
}
