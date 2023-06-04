package com.sahu.um.service;

import java.util.List;

import com.sahu.um.model.Permission;
import com.sahu.um.service.dto.PermissionDTO;

public interface PermissionService {
	
	public List<PermissionDTO> findAll();
	
	public List<Permission> findByIdIn(List<Long> ids);
	
}
