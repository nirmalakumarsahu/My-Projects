package com.sahu.dp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahu.dp.model.Permission;
import com.sahu.dp.repository.PermissionRepository;
import com.sahu.dp.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public List<Permission> findAll() {
		return permissionRepository.findByActive(true);
	}

	@Override
	public List<Permission> findByIdIn(List<Long> ids) {
		return permissionRepository.findAllById(ids);
	}

	@Override
	public List<Permission> getAllPermissionsOfUserByUserId(Long userId) {
		return permissionRepository.getAllPermissionsOfUserByUserId(userId);
	}

}
