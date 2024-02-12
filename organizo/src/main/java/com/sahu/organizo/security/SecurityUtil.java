package com.sahu.organizo.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.sahu.organizo.constants.RoleConstants;
import com.sahu.organizo.security.dto.CustomUserDetailsDTO;

@Component
public class SecurityUtil {

	public static CustomUserDetailsDTO getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CustomUserDetailsDTO) {
			return ((CustomUserDetailsDTO) principal);
		} else {
			return null;
		}
	}

	public static Boolean isGlobalAdmin(CustomUserDetailsDTO loggedInUser) {
		if (loggedInUser == null) {
			loggedInUser = getCurrentUser();
		}

		List<String> loggedInUserRoles = loggedInUser.getUserRoles();

		if (loggedInUserRoles.contains(RoleConstants.GLOBAL_ADMIN)) {
			return true;
		}

		return false;
	}

	public static Boolean hasRole(String role) {
		CustomUserDetailsDTO user = getCurrentUser();
		if (user != null) {
			if (isGlobalAdmin(user)) {
				return true;
			} else {
				List<String> roleList = user.getUserRoles();

				return roleList.contains(role);
			}
		}

		return false;
	}

	public static Boolean hasAllRoles(String... roles) {
		CustomUserDetailsDTO user = getCurrentUser();
		if (user != null) {
			if (isGlobalAdmin(user)) {
				return true;
			} else {
				List<String> roleList = user.getUserRoles();

				return roleList.containsAll(Arrays.asList(roles));
			}
		}

		return false;
	}

	public static Boolean hasAnyRole(String... roles) {
		CustomUserDetailsDTO user = getCurrentUser();
		if (user != null) {
			if (isGlobalAdmin(user)) {
				return true;
			} else {
				List<String> roleList = user.getUserRoles();

				if (roleList != null && roles.length > 0) {
					if (CollectionUtils.containsAny(roleList, Arrays.asList(roles))) {
						return true;
					}
				}
			}
		}

		return false;
	}

}
