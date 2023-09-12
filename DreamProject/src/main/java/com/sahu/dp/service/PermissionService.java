package com.sahu.dp.service;

import java.util.List;

import com.sahu.dp.model.Permission;

public interface PermissionService {

	public List<Permission> findAll();

	public List<Permission> findByIdIn(List<Long> ids);
	
	public List<Permission> getAllPermissionsOfUserByUserId(Long userId);

}
