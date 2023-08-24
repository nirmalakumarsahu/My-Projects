package com.sahu.util.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.sahu.constants.PermissionConstants;
import com.sahu.service.dto.CustomUserDetailsDTO;

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
		
		List<String> loggedInUserPermissions = loggedInUser.getUserPermissions();

		if (loggedInUserPermissions.contains(PermissionConstants.GLOBAL_ADMINISTRATION)) {
			return true;
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
	
	public static Boolean hasAnyPermission(String... permissions) {
		CustomUserDetailsDTO user = getCurrentUser();
		if (user != null) {
			if (isGlobalAdmin(user)) {
				return true;
			} else {
				List<String> permissionList = user.getUserPermissions();

				if(permissionList!=null && permissions.length>0) {
					if(CollectionUtils.containsAny(permissionList, Arrays.asList(permissions))) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

}
