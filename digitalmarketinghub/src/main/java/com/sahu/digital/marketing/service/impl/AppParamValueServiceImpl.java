package com.sahu.digital.marketing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahu.digital.marketing.model.AppParamValue;
import com.sahu.digital.marketing.repository.AppParamValueRepository;
import com.sahu.digital.marketing.service.AppParamValueService;

@Service
public class AppParamValueServiceImpl implements AppParamValueService {

	@Autowired
	private AppParamValueRepository appParamValueRepository;

	@Override
	public List<AppParamValue> findByAppParamGroupName(String appParamGroupName) {
		return appParamValueRepository.findByAppParamGroupName(appParamGroupName);
	}

}
