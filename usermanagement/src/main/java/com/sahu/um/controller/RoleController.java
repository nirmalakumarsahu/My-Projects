package com.sahu.um.controller;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sahu.um.constants.LVNConstants;
import com.sahu.um.constants.UserManagementConstants;
import com.sahu.um.model.Role;
import com.sahu.um.service.PermissionService;
import com.sahu.um.service.RoleService;
import com.sahu.um.service.dto.PermissionDTO;
import com.sahu.um.service.dto.RoleDTO;

@Controller
@RequestMapping("/client/role")
public class RoleController {
	
	private final Logger LOGGER = Logger.getLogger(UserController.class);
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;
	
	@GetMapping("/list")
	public String showRoleListPage() {
		return LVNConstants.ROLE_LIST_PAGE;
	}	
	
	@GetMapping("/add")
	public String showAddRolePage(Model map) {
		List<PermissionDTO> permissionDTOList = permissionService.findAll();
		map.addAttribute("permissionList", permissionDTOList);
		return LVNConstants.ADD_EDIT_ROLE_PAGE;
	}
	
	@PostMapping("/add")
	public String addRole(@ModelAttribute RoleDTO roleDTO, RedirectAttributes redirectAttributes) {
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
