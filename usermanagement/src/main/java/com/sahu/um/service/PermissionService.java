package com.sahu.um.service;

import java.util.List;

import com.sahu.um.model.Permission;

public interface PermissionService {
	
	public List<Permission> findAll();
	
	public List<Permission> findByIdIn(List<Long> ids);
	
}
