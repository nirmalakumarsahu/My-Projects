package com.sahu.um.controller.rest;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sahu.um.service.dto.RoleDTO;
import com.sahu.um.service.specification.RolePageSpecification;

@RestController
@RequestMapping("/api/role")
public class RoleRestController {

	@Autowired
	private RolePageSpecification rolePageSpecification;
	
	@GetMapping("/list")
	public DataTablesOutput<RoleDTO> getRoles(@Valid DataTablesInput dataTablesInput, @RequestParam Map<String, String> queryParams) {
		return rolePageSpecification.getUsers(dataTablesInput, queryParams);
	}
}
