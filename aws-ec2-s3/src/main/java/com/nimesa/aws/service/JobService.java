package com.nimesa.aws.service;

import java.util.List;

import com.nimesa.aws.model.Job;

public interface JobService {

	Job addJob(List<String> services);
	
	Job updateJob(Job job);
	
	Job findById(Long id);
	
	Job addJob(String bucketName);

}
