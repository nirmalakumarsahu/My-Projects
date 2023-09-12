package com.sahu.dp.controller;

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

import com.sahu.dp.constants.CommonConstants;
import com.sahu.dp.constants.LVNConstants;
import com.sahu.dp.model.User;
import com.sahu.dp.security.SecurityUtil;
import com.sahu.dp.service.UserService;
import com.sahu.dp.service.dto.CustomLoginUserDTO;
import com.sahu.dp.service.dto.UserDTO;

@Controller
@RequestMapping("/client/user")
public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/dashboard")
	public String showDashBoardPage() {
		//CustomUserDetailsDTO loggedInUserDetails = SecurityUtil.getCurrentUser();
		//LOGGER.info("Roles - " + loggedInUserDetails.getUserRoles());
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

	@GetMapping({ "/edit-profile", "/action2" })
	public String showEditProfilePage() {
		return LVNConstants.EDIT_PROFILE_PAGE;
	}

	@PostMapping("/add")
	public String addUser(@ModelAttribute("userDTO") UserDTO userDTO, RedirectAttributes redirectAttributes) {
		LOGGER.debug("Inside addUser() method");
		LOGGER.info("User Details - " + userDTO.getEmail());

		Optional<User> isUserExist = userService.findByEmail(userDTO.getEmail());
		if (isUserExist.isPresent()) {
			redirectAttributes.addFlashAttribute(CommonConstants.ERROR, "User already exist");
		} else {
			
		}

		return "redirect:/client/user/add";
	}

}
