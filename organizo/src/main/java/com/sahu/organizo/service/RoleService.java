package com.sahu.organizo.service;

import com.sahu.organizo.model.Role;

public interface RoleService {

	Role findByName(String name);

	Role findById(Long id);

}
