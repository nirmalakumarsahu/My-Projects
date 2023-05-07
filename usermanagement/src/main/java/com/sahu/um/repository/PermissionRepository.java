package com.sahu.um.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahu.um.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
