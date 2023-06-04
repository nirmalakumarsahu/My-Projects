package com.sahu.um.service;

import java.util.Optional;

import com.sahu.um.model.Role;
import com.sahu.um.service.dto.RoleDTO;

public interface RoleService {

	public Optional<Role> findByName(String name); 
	
	public Boolean addRole(RoleDTO roleDTO);
	
}
