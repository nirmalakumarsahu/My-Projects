package com.sahu.um.service.util;

import java.util.List;
import java.util.stream.Collectors;

import com.sahu.um.model.Role;
import com.sahu.um.service.dto.RoleDTO;

public class RoleUtil {

	public static List<RoleDTO> toPermissionDTO(List<Role> roleList) {
		return roleList.stream().map(role -> new RoleDTO(role.getId(), role.getName(), role.getPermissions()))
				.collect(Collectors.toList());
	}

}
