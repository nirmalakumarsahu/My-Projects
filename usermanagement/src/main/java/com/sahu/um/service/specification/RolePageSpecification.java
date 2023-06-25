package com.sahu.um.service.specification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.sahu.um.model.Role;
import com.sahu.um.service.RoleService;
import com.sahu.um.service.dto.RoleDTO;
import com.sahu.um.util.service.RoleUtil;

@Component
public class RolePageSpecification {

	@Autowired
	private RoleService roleSerivce;

	public DataTablesOutput<RoleDTO> getUsers(DataTablesInput dataTablesInput, Map<String, String> queryParams) {

		Specification<Role> roleSpecification = (Specification<Role>) (root, query, criteriaBuilder) -> {
			return criteriaBuilder.equal(root.get("active"), true);
		};

		DataTablesOutput<Role> dataTablesOutputRole = null;
		if (roleSpecification != null)
			dataTablesOutputRole = roleSerivce.findAll(dataTablesInput, roleSpecification);

		List<RoleDTO> roleDTOList = null;
		if (dataTablesOutputRole.getData() != null) {
			roleDTOList = dataTablesOutputRole.getData().stream().map(role -> RoleUtil.toRoleDTO(role))
					.collect(Collectors.toList());
		}

		DataTablesOutput<RoleDTO> dataTablesOutputRoleDTO = new DataTablesOutput<>();
		dataTablesOutputRoleDTO.setData(roleDTOList);
		dataTablesOutputRoleDTO.setDraw(dataTablesOutputRole.getDraw());
		dataTablesOutputRoleDTO.setError(dataTablesOutputRole.getError());
		dataTablesOutputRoleDTO.setRecordsFiltered(dataTablesOutputRole.getRecordsFiltered());
		dataTablesOutputRoleDTO.setRecordsTotal(dataTablesOutputRole.getRecordsTotal());
		dataTablesOutputRoleDTO.setSearchPanes(dataTablesOutputRole.getSearchPanes());

		return dataTablesOutputRoleDTO;
	}

}
