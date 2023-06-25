package com.sahu.um.util.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sahu.um.model.Permission;
import com.sahu.um.service.dto.PermissionDTO;

@Component
public class PermissionUtil {

	public static List<PermissionDTO> toPermissionDTO(List<Permission> permissionList) {
		return permissionList.stream().map(permission -> toPermissionDTO(permission)).collect(Collectors.toList());
	}

	public static PermissionDTO toPermissionDTO(Permission permission) {
		return new PermissionDTO(permission.getId(), permission.getName());
	}

}
