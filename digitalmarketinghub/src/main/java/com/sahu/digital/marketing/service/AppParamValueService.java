package com.sahu.digital.marketing.service;

import java.util.List;

import com.sahu.digital.marketing.model.AppParamValue;

public interface AppParamValueService {

	public List<AppParamValue> findByAppParamGroupName(String appParamGroupName);

}
