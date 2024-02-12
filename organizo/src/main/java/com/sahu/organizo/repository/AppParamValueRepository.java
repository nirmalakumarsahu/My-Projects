package com.sahu.organizo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahu.organizo.model.AppParamValue;

public interface AppParamValueRepository extends JpaRepository<AppParamValue, Long> {

	List<AppParamValue> findByAppParamGroupName(String appParamGroupName);

}
