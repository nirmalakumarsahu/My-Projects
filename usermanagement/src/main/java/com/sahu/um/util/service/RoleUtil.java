package com.sahu.um.util.service;

import java.util.List;
import java.util.stream.Collectors;

import com.sahu.um.model.Role;
import com.sahu.um.service.dto.RoleDTO;

public class RoleUtil {

	public static List<RoleDTO> toRoleDTO(List<Role> roleList) {
		return roleList.stream().map(role -> toRoleDTO(role)).collect(Collectors.toList());
	}

	public static RoleDTO toRoleDTO(Role role) {
		return new RoleDTO(role.getId(), role.getName(), role.getPermissions());
	}

}
