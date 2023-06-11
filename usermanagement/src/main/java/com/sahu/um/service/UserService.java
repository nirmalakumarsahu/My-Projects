package com.sahu.um.service;

import java.util.Optional;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;

import com.sahu.um.model.User;
import com.sahu.um.service.dto.UserDTO;

public interface UserService {

	public Optional<User> findByEmail(String email);
	
	public Long registerUser(User user);
	
	public DataTablesOutput<User> findAll(DataTablesInput dataTablesInput);

	public DataTablesOutput<User> findAll(DataTablesInput dataTablesInput, Specification<User> specification);
	
	public Boolean saveUser(UserDTO userDTO);
	
}
