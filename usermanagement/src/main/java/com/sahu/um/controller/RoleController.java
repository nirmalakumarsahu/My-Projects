package com.sahu.um.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sahu.um.constants.LVNConstants;

@Controller
@RequestMapping("/client/role")
public class RoleController {
	
	@GetMapping("/list")
	public String showRoleListPage() {
		return LVNConstants.ROLE_LIST_PAGE;
	}	
	
	@GetMapping("/add")
	public String showAddRolePage() {
		return LVNConstants.ADD_EDIT_ROLE_PAGE;
	}
	
}
