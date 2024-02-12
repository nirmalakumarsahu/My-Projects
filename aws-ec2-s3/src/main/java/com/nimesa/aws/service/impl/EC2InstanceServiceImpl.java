package com.nimesa.aws.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimesa.aws.model.EC2Instance;
import com.nimesa.aws.repository.EC2InstanceRepository;
import com.nimesa.aws.service.EC2InstanceService;

@Service
public class EC2InstanceServiceImpl implements EC2InstanceService {

	@Autowired
	private EC2InstanceRepository ec2InstanceRepository;

	@Override
	public void saveEC2Instances(List<String> instanceIds) {
		List<EC2Instance> instnces = instanceIds.stream().map(EC2Instance::new).collect(Collectors.toList());
		ec2InstanceRepository.saveAll(instnces);
	}

	@Override
	public List<String> findAllInstanceIds() {
		List<EC2Instance> instances = ec2InstanceRepository.findAll();
		return instances != null ? instances.stream().map(EC2Instance::getInstanceId).collect(Collectors.toList())
				: null;
	}

}
