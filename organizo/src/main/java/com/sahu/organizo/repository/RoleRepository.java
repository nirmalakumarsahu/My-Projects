package com.sahu.organizo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahu.organizo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);

}
