package com.sahu.um.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahu.um.model.Permission;
import com.sahu.um.repository.PermissionRepository;
import com.sahu.um.service.PermissionService;
import com.sahu.um.service.dto.PermissionDTO;
import com.sahu.um.service.util.PermissionUtil;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	public List<PermissionDTO> findAll() {
		return PermissionUtil.toPermissionDTO(permissionRepository.findByActive(true));
	}

	@Override
	public List<Permission> findByIdIn(List<Long> ids) {
		List<Permission> PermissionList = new ArrayList<>();
		permissionRepository.findAllById(ids).forEach(PermissionList::add);
		return PermissionList;
	}
	
}
