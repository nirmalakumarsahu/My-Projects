package com.sahu.digital.marketing.service;

import java.util.List;

import com.sahu.digital.marketing.model.Permission;

public interface PermissionService {

	public List<Permission> findAll();

	public List<Permission> findByIdIn(List<Long> ids);

	public List<Permission> getAllPermissionsOfUserByUserId(Long userId);

}
