package com.sahu.digital.marketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sahu.digital.marketing.model.AppParamValue;

public interface AppParamValueRepository extends JpaRepository<AppParamValue, Long> {

	@Query(value = "SELECT apv.* FROM app_param_value AS apv \r\n"
			+ "INNER JOIN app_param_group AS apg ON apv.app_param_group_id = apg.id \r\n"
			+ "WHERE apg.name = :appParamGroupName AND apv.active = true", nativeQuery = true)
	public List<AppParamValue> findByAppParamGroupName(String appParamGroupName);

}
