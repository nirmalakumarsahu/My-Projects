package com.sahu.dp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sahu.dp.constants.CommonConstants;
import com.sahu.dp.constants.LVNConstants;
import com.sahu.dp.model.Permission;
import com.sahu.dp.model.Role;
import com.sahu.dp.service.PermissionService;
import com.sahu.dp.service.RoleService;
import com.sahu.dp.service.dto.RoleDTO;
import com.sahu.dp.util.RoleUtil;

@Controller
@RequestMapping("/client/role")
public class RoleController {

	private Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleUtil roleUtil;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@GetMapping("/list")
	public String showRoleListPage() {
		return LVNConstants.ROLE_LIST_PAGE;
	}

	@GetMapping("/add")
	public String addRole(Map<String, Object> map) {
		List<Permission> permissionDTOList = permissionService.findAll();
		map.put("permissionList", permissionDTOList);
		return LVNConstants.ADD_EDIT_ROLE_PAGE;
	}

	@PostMapping("/add")
	public String addRole(@ModelAttribute("roleDTO") RoleDTO roleDTO, RedirectAttributes redirectAttributes) {
		LOGGER.debug("Inside addRole() method");
		Optional<Role> isRoleExist = roleService.findByName(roleDTO.getName());

		if (isRoleExist.isPresent()) {
			redirectAttributes.addFlashAttribute(CommonConstants.ERROR, "Role already exist");
		} else {
			if (roleUtil.saveRole(roleDTO))
				redirectAttributes.addFlashAttribute(CommonConstants.SUCCESS, "Role added successfully");
			else
				redirectAttributes.addFlashAttribute(CommonConstants.ERROR, "Error while adding Role");
		}

		return "redirect:/client/role/add";
	}

	@GetMapping("/{id}/edit")
	public String editRole(@PathVariable(name = "id") Long id, Map<String, Object> map) {
		Optional<Role> optionalRole = roleService.findById(id);
		if (optionalRole.isPresent()) {
			Role role = optionalRole.get();
			List<Permission> permissionDTOList = permissionService.findAll();
			map.put("permissionList", permissionDTOList);
			map.put("id", role.getId());
			map.put("name", role.getName());
			map.put("permissions", role.getPermissions());
		} else {
			map.put(CommonConstants.ERROR, "Role is not exist");
		}
		return LVNConstants.ADD_EDIT_ROLE_PAGE;
	}

	@PostMapping("/{id}/edit")
	public String editRole(@ModelAttribute("roleDTO") RoleDTO roleDTO, RedirectAttributes redirectAttributes) {
		LOGGER.debug("Inside editRole() method");
		if (roleUtil.saveRole(roleDTO))
			redirectAttributes.addFlashAttribute(CommonConstants.SUCCESS, "Role updated successfully");
		else
			redirectAttributes.addFlashAttribute(CommonConstants.ERROR, "Error while updating Role");

		return "redirect:/client/role/add";
	}

}
