package com.sahu.um.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahu.um.model.Permission;
import com.sahu.um.model.Role;
import com.sahu.um.repository.RoleRepository;
import com.sahu.um.service.PermissionService;
import com.sahu.um.service.RoleService;
import com.sahu.um.service.dto.RoleDTO;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public Optional<Role> findByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Boolean addRole(RoleDTO roleDTO) {
		Role role = new Role();
		List<Permission> permissionList = permissionService.findByIdIn(roleDTO.getPermissions());
		
		role.setName(roleDTO.getName());
		role.setActive(true);
		role.setPermissions(permissionList);
		return roleRepository.save(role)!=null?true:false;
	}
	
}
