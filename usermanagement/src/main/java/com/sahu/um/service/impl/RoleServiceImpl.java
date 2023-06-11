package com.sahu.um.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sahu.um.model.Role;
import com.sahu.um.repository.RoleRepository;
import com.sahu.um.service.RoleService;
import com.sahu.um.service.dto.RoleDTO;
import com.sahu.um.util.security.SecurityUtil;

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
	public Boolean saveRole(RoleDTO roleDTO) {
		Role role = null;
		if (roleDTO.getId() != null) {
			role = roleRepository.findById(roleDTO.getId()).get();
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

		return roleRepository.save(role) != null ? true : false;
	}

	@Override
	public DataTablesOutput<Role> findAll(DataTablesInput dataTablesInput) {
		return roleRepository.findAll(dataTablesInput);
	}

	@Override
	public DataTablesOutput<Role> findAll(DataTablesInput dataTablesInput, Specification<Role> specification) {
		return roleRepository.findAll(dataTablesInput, specification);
	}

}
