package com.sahu.service;

import com.sahu.request.dto.LoginDTO;
import com.sahu.response.dto.JWTAuthResponseDTO;

public interface AuthenticationService {
	public JWTAuthResponseDTO getAccessToken(LoginDTO loginDTO);
}
