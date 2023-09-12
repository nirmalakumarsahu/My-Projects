package com.sahu.dp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahu.dp.model.Role;
import com.sahu.dp.repository.RoleRepository;
import com.sahu.dp.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Optional<Role> findByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Optional<Role> findById(Long id) {
		return roleRepository.findById(id);
	}

	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public List<Role> getRolesOfUserByUserId(Long userId) {
		return roleRepository.getRolesOfUserByUserId(userId);
	}

}
