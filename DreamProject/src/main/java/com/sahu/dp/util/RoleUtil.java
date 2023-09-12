package com.sahu.dp.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sahu.dp.model.Role;
import com.sahu.dp.security.SecurityUtil;
import com.sahu.dp.service.RoleService;
import com.sahu.dp.service.dto.RoleDTO;

@Component
public class RoleUtil {
	
	@Autowired
	private RoleService roleService;
	
	public Boolean saveRole(RoleDTO roleDTO) {
		Role role = null;
		if (roleDTO.getId() != null) {
			role = roleService.findById(roleDTO.getId()).get();
			role.setName(roleDTO.getName());
			role.setPermissions(roleDTO.getPermissions());
			role.setUpdatedAt(new Date());
			role.setUpdatedBy(SecurityUtil.getCurrentUser().getUserId());
		} else {
			role = new Role();
			role.setName(roleDTO.getName());
			role.setActive(true);
			role.setPermissions(roleDTO.getPermissions());
		}

		return roleService.save(role) != null ? true : false;
	}
	
}
