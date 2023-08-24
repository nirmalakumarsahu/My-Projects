package com.sahu.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahu.request.dto.LoginDTO;
import com.sahu.response.dto.JWTAuthResponseDTO;
import com.sahu.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticate(@RequestBody LoginDTO loginDTO){
		JWTAuthResponseDTO tokenDetails = authenticationService.getAccessToken(loginDTO);
        return ResponseEntity.ok(tokenDetails);
    }

}
