package com.sahu.dp.service.dto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomOAuth2UserDTO implements OAuth2User {

	private String oauth2ClientName;
	private OAuth2User oAuth2User;
	private Long userId;
	private List<String> userRoles;
	private List<String> userPermissions;

	@Override
	public Map<String, Object> getAttributes() {
		return oAuth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oAuth2User.getAuthorities();
	}

	@Override
	public String getName() {
		return oAuth2User.getName();
	}

	public String getEmail() {
		return oAuth2User.getAttribute("email");
	}

}
