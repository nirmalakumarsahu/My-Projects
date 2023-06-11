package com.sahu.um.service.dto;

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

	private List<String> userRoles;

	private List<String> userPermissions;

	public CustomUserDetailsDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUserDetailsDTO(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Long userId, List<String> userRoles, List<String> userPermissions) {
		super(username, password, authorities);
		this.userId = userId;
		this.userRoles = userRoles;
		this.userPermissions = userPermissions;
	}

}
