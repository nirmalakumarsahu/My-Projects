package com.sahu.um.service.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.sahu.um.model.User;
import com.sahu.um.service.UserService;
import com.sahu.um.service.dto.UserDTO;
import com.sahu.um.service.util.UserUtil;

@Component
public class UserPageSpecification {

	@Autowired
	private UserService userService;

	public DataTablesOutput<UserDTO> getUsers(DataTablesInput dataTablesInput, Map<String, String> queryParams) {

		dataTablesInput.parseSearchPanesFromQueryParams(queryParams, Arrays.asList("status"));
		Set<String> status = dataTablesInput.getSearchPanes().remove("status");
		Specification<User> userSpecification = createSimpleSpecification("status", status);

		DataTablesOutput<User> dataTablesOutputUser = null;
		if (userSpecification != null)
			dataTablesOutputUser = userService.findAll(dataTablesInput, userSpecification);
		else
			dataTablesOutputUser = userService.findAll(dataTablesInput);

		List<UserDTO> userDTOList = null;
		if (dataTablesOutputUser.getData() != null) {
			userDTOList = UserUtil.toUserDTo(dataTablesOutputUser.getData());
		}

		DataTablesOutput<UserDTO> dataTablesOutputUserDTO = new DataTablesOutput<>();
		dataTablesOutputUserDTO.setData(userDTOList);
		dataTablesOutputUserDTO.setDraw(dataTablesOutputUser.getDraw());
		dataTablesOutputUserDTO.setError(dataTablesOutputUser.getError());
		dataTablesOutputUserDTO.setRecordsFiltered(dataTablesOutputUser.getRecordsFiltered());
		dataTablesOutputUserDTO.setRecordsTotal(dataTablesOutputUser.getRecordsTotal());
		dataTablesOutputUserDTO.setSearchPanes(dataTablesOutputUser.getSearchPanes());

		return dataTablesOutputUserDTO;
	}

	private Specification<User> createSimpleSpecification(String fieldName, Set<String> entries) {
		if (entries.isEmpty()) {
			return null;
		}
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (entries.contains("null")) {
				predicates.add(root.get(fieldName).isNull());
			} else {
				predicates.add(root.get(fieldName).in(entries));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
