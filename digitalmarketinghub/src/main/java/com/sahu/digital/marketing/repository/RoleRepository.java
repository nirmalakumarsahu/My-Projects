package com.sahu.digital.marketing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahu.digital.marketing.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Optional<Role> findByRoleKey(String name);

}
