package com.sahu.dp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sahu.dp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query(value = "SELECT r.* FROM role r INNER JOIN role_user ru ON ru.role_id = r.id "
			+ "WHERE ru.user_id=:userId AND r.active IS TRUE", nativeQuery = true)
	public List<Role> getRolesOfUserByUserId(Long userId);
	
	public Optional<Role> findByName(String name);
	
}
