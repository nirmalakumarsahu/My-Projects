package com.sahu.um.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sahu.um.constants.PermissionConstants;
import com.sahu.um.service.dto.CustomUserDetailsDTO;

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
		} else {
			List<String> loggedInUserPermissions = loggedInUser.getUserPermissions();

			if (loggedInUserPermissions.contains(PermissionConstants.GLOBAL_ADMINISTRATION)) {
				return true;
			}
		}
		return false;
	}

	public static Boolean hasPermission(String permission) {
		CustomUserDetailsDTO user = getCurrentUser();
		if (user != null) {
			if (isGlobalAdmin(user)) {
				return true;
			} else {
				List<String> permissions = user.getUserPermissions();

				return permissions.contains(permission);
			}
		}
		
		return false;
	}
	
	public static Boolean hasAllPermissions(String... permissions) {
		CustomUserDetailsDTO user = getCurrentUser();
		if (user != null) {
			if (isGlobalAdmin(user)) {
				return true;
			} else {
				List<String> permissionList = user.getUserPermissions();

				return permissionList.containsAll(Arrays.asList(permissions));
			}
		}
		
		return false;
	}
	
	public static Boolean hasAnyPermissions(String... permissions) {
		CustomUserDetailsDTO user = getCurrentUser();
		if (user != null) {
			if (isGlobalAdmin(user)) {
				return true;
			} else {
				List<String> permissionList = user.getUserPermissions();

				for (String permission : permissions) {
					return permissionList.contains(permission);
				}
			}
		}
		
		return false;
	}

}
