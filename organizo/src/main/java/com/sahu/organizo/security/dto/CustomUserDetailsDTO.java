package com.sahu.organizo.security.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomUserDetailsDTO extends User {

	private static final long serialVersionUID = 1L;

	private Long userId;
	private String uuid;
	private String displayName;
	private String email;
	private List<String> userRoles;
	
	public CustomUserDetailsDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUserDetailsDTO(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Long userId, String uuid, String displayName, String email, List<String> userRoles) {
		super(username, password, authorities);
		this.userId = userId;
		this.uuid = uuid;
		this.displayName = displayName;
		this.email = email;
		this.userRoles = userRoles;
	}

}
