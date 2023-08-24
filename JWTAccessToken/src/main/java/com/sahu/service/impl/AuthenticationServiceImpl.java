package com.sahu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sahu.request.dto.LoginDTO;
import com.sahu.response.dto.JWTAuthResponseDTO;
import com.sahu.service.AuthenticationService;
import com.sahu.util.JwtTokenProvider;
import com.sahu.util.security.SecurityUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public JWTAuthResponseDTO getAccessToken(LoginDTO loginDTO) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateAccessToken(SecurityUtil.getCurrentUser());
		JWTAuthResponseDTO jwtAuthResponse = new JWTAuthResponseDTO();
		jwtAuthResponse.setAccessToken(token);
		jwtAuthResponse.setExpirationDate(jwtTokenProvider.getExpirationDate(token));
		return jwtAuthResponse;
	}

}
