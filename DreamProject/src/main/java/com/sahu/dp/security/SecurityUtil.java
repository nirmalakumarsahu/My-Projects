package com.sahu.dp.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.sahu.dp.constants.PermissionConstants;
import com.sahu.dp.service.dto.CustomLoginUserDTO;

@Component
public class SecurityUtil {

	public static CustomLoginUserDTO getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CustomLoginUserDTO) {
			return ((CustomLoginUserDTO) principal);
		} else {
			return null;
		}
	}

	public static Boolean isGlobalAdmin(CustomLoginUserDTO loggedInUser) {
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
		CustomLoginUserDTO user = getCurrentUser();
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
		CustomLoginUserDTO user = getCurrentUser();
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
		CustomLoginUserDTO user = getCurrentUser();
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
