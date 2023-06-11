package com.sahu.um.service.dto;

import java.util.List;

import com.sahu.um.model.Permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
	private Long id;
	private String name;
	private List<Permission> permissions;
}
