package com.sahu.um.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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

@Component
public class UserPageSpecification {

	@Autowired
	private UserService userService;
	
	public DataTablesOutput<UserDTO> getUsers(DataTablesInput dataTablesInput, Map<String, String> queryParams) {
		dataTablesInput.parseSearchPanesFromQueryParams(queryParams, Arrays.asList("status"));
		Specification<User> specification = createSimpleSpecification("status", new HashSet<Object>(dataTablesInput.getSearchPanes().remove("status")));
		
		userService.findAll(dataTablesInput, specification);
		return null;
	} 
	
	
	private Specification<User> createSimpleSpecification(String fieldName, Set<Object> entries) {
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
