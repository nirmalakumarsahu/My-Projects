package com.sahu.digital.marketing.service.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.sahu.digital.marketing.security.SecurityUtil;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomUserDetailsDTO extends User {
	
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String uuid;
	private String fullName;
	private String email;
	private Long phoneNo;
	private List<String> userRoles;
	private List<String> userPermissions;

	public CustomUserDetailsDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUserDetailsDTO(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Long userId, String uuid, String fullName, String email, Long phoneNo, List<String> userRoles, List<String> userPermissions) {
		super(username, password, authorities);
		this.userId = userId;
		this.uuid = uuid;
		this.fullName = fullName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.userRoles = userRoles;
		this.userPermissions = userPermissions;
	}

	public Boolean isGlobalAdmin() {
		return SecurityUtil.isGlobalAdmin(null);
	}

	public Boolean hasPermission(String permission) {
		return SecurityUtil.hasPermission(permission);
	}

	public Boolean hasAllPermissions(String... permissions) {
		return SecurityUtil.hasAllPermissions(permissions);
	}

	public Boolean hasAnyPermission(String... permissions) {
		return SecurityUtil.hasAnyPermission(permissions);
	}

}
