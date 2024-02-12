package com.sahu.digital.marketing.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahu.digital.marketing.model.Role;
import com.sahu.digital.marketing.repository.RoleRepository;
import com.sahu.digital.marketing.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role findByRoleKey(String name) {
		Optional<Role> optionalRole = roleRepository.findByRoleKey(name);
		return optionalRole.isPresent() ? optionalRole.get() : null;
	}

	@Override
	public Role findById(Long id) {
		Optional<Role> optionalRole = roleRepository.findById(id);
		return optionalRole.isPresent() ? optionalRole.get() : null;
	}

}
