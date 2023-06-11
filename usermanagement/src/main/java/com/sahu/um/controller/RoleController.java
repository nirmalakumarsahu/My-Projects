package com.sahu.um.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sahu.um.constants.LVNConstants;
import com.sahu.um.constants.UserManagementConstants;
import com.sahu.um.model.Permission;
import com.sahu.um.model.Role;
import com.sahu.um.service.PermissionService;
import com.sahu.um.service.RoleService;
import com.sahu.um.service.dto.RoleDTO;

@Controller
@RequestMapping("/client/role")
public class RoleController {
	
	private Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;
	
	@GetMapping("/list")
	public String showRoleListPage() {
		return LVNConstants.ROLE_LIST_PAGE;
	}	
	
	@GetMapping("/add")
	public String showAddRolePage(Map<String, Object> map) {
		List<Permission> permissionDTOList = permissionService.findAll();
		map.put("permissionList", permissionDTOList);
		return LVNConstants.ADD_EDIT_ROLE_PAGE;
	}
	
	@PostMapping("/add")
	public String addRole(@ModelAttribute("roleDTO") RoleDTO roleDTO, RedirectAttributes redirectAttributes) {
		LOGGER.debug("Inside addRole() method");
		Optional<Role> isRoleExist = roleService.findByName(roleDTO.getName());
		
		if(isRoleExist.isPresent()) {
			redirectAttributes.addFlashAttribute(UserManagementConstants.ERROR, "Role already exist");
		}
		else {
			if(roleService.addRole(roleDTO))
				redirectAttributes.addFlashAttribute(UserManagementConstants.SUCCESS, "Role added successfully");
			else
				redirectAttributes.addFlashAttribute(UserManagementConstants.ERROR, "Error while adding Role");
		}
		
		return "redirect:/client/role/add";
	}

}
