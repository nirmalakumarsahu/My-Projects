package com.sahu.digital.marketing.service;

import com.sahu.digital.marketing.model.Role;

public interface RoleService {

	public Role findByRoleKey(String name);

	public Role findById(Long id);

}
