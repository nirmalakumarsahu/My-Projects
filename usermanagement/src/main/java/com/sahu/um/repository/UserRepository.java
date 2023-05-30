package com.sahu.um.repository;

import java.util.Optional;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.sahu.um.model.User;

public interface UserRepository extends DataTablesRepository<User, Long> {
	
	public Optional<User> findByEmail(String email);
	
	
}
