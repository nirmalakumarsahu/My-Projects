package com.sahu.um.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.sahu.um.model.User;
import com.sahu.um.service.UserService;
import com.sahu.um.service.dto.CustomUserDetailsDTO;
import com.sahu.um.service.dto.UserDTO;
import com.sahu.um.util.security.SecurityUtil;

@Controller
@RequestMapping("/client/user")
public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/dashboard")
	public String showDashBoardPage() {
		CustomUserDetailsDTO loggedInUserDetails = SecurityUtil.getCurrentUser();
		LOGGER.info("Roles - "+loggedInUserDetails.getUserRoles());
		return LVNConstants.DASHBOARD_PAGE;
	}
	
	@GetMapping("/list")
	public String showUserListPage() {
		return LVNConstants.USER_LIST_PAGE;
	}
	
	@GetMapping("/add")
	public String showAddUserPage() {
		return LVNConstants.ADD_EDIT_USER_PAGE;
	}
	
	@GetMapping("/assign-role")
	public String showAssignRolePage() {
		return LVNConstants.ASSIGN_ROLE_PAGE;
	}
	
	@GetMapping({"/edit-profile", "/action2"})
	public String showEditProfilePage() {
		return LVNConstants.EDIT_PROFILE_PAGE;
	}
	
	@PostMapping("/add")
	public String addUser(@ModelAttribute UserDTO userDTO, Model model, RedirectAttributes redirectAttributes) {
		LOGGER.debug("Inside addUser() method");
		LOGGER.info("User Details - "+userDTO.getEmail());
		
		Optional<User> isUserExist = userService.findByEmail(userDTO.getEmail());
		if(isUserExist.isPresent()) {
			redirectAttributes.addFlashAttribute(UserManagementConstants.ERROR, "User already exist");
		}
		else {
			if(userService.addUser(userDTO))
				redirectAttributes.addFlashAttribute(UserManagementConstants.SUCCESS, "User added successfully");
			else
				redirectAttributes.addFlashAttribute(UserManagementConstants.ERROR, "Error while adding User");
		}
		
		return "redirect:/client/user/add";
	}
	
}
