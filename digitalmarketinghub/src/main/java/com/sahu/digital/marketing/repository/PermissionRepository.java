package com.sahu.digital.marketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sahu.digital.marketing.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

	@Query(value = "SELECT p.* FROM permission AS p	\r\n"
			+ "INNER JOIN role_permission AS rp ON p.id = rp.permission_id \r\n"
			+ "INNER JOIN role AS r ON rp.role_id = r.id \r\n" + "INNER JOIN user_role AS ur ON r.id = ur.role_id \r\n"
			+ "WHERE ur.user_id = :userId AND p.active = true", nativeQuery = true)
	public List<Permission> getAllPermissionsOfUserByUserId(Long userId);

	public List<Permission> findByActive(Boolean active);

}
