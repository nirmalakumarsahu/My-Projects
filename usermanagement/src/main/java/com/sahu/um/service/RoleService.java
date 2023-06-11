package com.sahu.um.service;

import java.util.Optional;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;

import com.sahu.um.model.Role;
import com.sahu.um.service.dto.RoleDTO;

public interface RoleService {

	public Optional<Role> findByName(String name);

	public Optional<Role> findById(Long id);

	public Boolean saveRole(RoleDTO roleDTO);

	public DataTablesOutput<Role> findAll(DataTablesInput dataTablesInput);

	public DataTablesOutput<Role> findAll(DataTablesInput dataTablesInput, Specification<Role> specification);

}
