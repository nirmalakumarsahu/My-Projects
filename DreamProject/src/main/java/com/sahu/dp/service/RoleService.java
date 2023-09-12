package com.sahu.dp.service;

import java.util.List;
import java.util.Optional;

import com.sahu.dp.model.Role;

public interface RoleService {

	public Optional<Role> findByName(String name);

	public Optional<Role> findById(Long id);

	public Role save(Role role);
	
	public List<Role> getRolesOfUserByUserId(Long userId);
	
}
