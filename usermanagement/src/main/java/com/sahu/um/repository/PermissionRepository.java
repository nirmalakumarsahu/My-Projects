package com.sahu.um.repository;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

import com.sahu.um.model.Permission;

public interface PermissionRepository extends DataTablesRepository<Permission, Long> {

	@Query(value ="SELECT p.* FROM permission p "
			+ "INNER JOIN role_permission rp ON rp.permission_id = p.id "
			+ "INNER JOIN role r ON r.id = rp.role_id "
			+ "INNER JOIN role_user ru ON ru.role_id = r.id "
			+ "WHERE ru.user_id =:userId AND p.active IS TRUE", nativeQuery = true)
	public List<Permission> getAllPermissionsOfUserByUserId(Long userId);
	
	public List<Permission> findByActive(Boolean active);
}
