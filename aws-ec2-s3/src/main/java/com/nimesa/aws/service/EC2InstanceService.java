package com.nimesa.aws.service;

import java.util.List;

public interface EC2InstanceService {
	
	void saveEC2Instances(List<String> instanceIds);
	
	List<String> findAllInstanceIds();
	
}
