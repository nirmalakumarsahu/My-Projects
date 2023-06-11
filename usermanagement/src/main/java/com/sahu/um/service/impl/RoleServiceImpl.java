package com.sahu.um.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahu.um.model.Role;
import com.sahu.um.repository.RoleRepository;
import com.sahu.um.service.RoleService;
import com.sahu.um.service.dto.RoleDTO;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Optional<Role> findByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Boolean addRole(RoleDTO roleDTO) {
		Role role = new Role();
		role.setName(roleDTO.getName());
		role.setActive(true);
		role.setPermissions(roleDTO.getPermissions());
		return roleRepository.save(role)!=null?true:false;
	}
	
}
