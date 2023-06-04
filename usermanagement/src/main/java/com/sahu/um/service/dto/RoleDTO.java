package com.sahu.um.service.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
	private Long id;
	private String name;
	private List<Long> permissions;
	private List<PermissionDTO> selectedPermissionList;
}
