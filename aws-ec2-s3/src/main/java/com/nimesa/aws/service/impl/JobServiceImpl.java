package com.nimesa.aws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimesa.aws.constants.AWSConstants;
import com.nimesa.aws.constants.Status;
import com.nimesa.aws.model.Job;
import com.nimesa.aws.repository.JobRepository;
import com.nimesa.aws.service.JobService;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepository jobRepository;

	@Override
	public Job addJob(List<String> services) {
		Job job = new Job();
		job.setSerivces(services.toString());
		job.setStatus(Status.INPROGRESS);
		return jobRepository.save(job);
	}

	@Override
	public Job updateJob(Job job) {
		return jobRepository.save(job);
	}

	@Override
	public Job findById(Long id) {
		Optional<Job> optJob = jobRepository.findById(id);
		return optJob.isPresent() ? optJob.get() : null;
	}

	@Override
	public Job addJob(String bucketName) {
		Job job = new Job();
		job.setSerivces(AWSConstants.MSG_FOR_GETTING_S3+bucketName);
		job.setStatus(Status.INPROGRESS);
		return jobRepository.save(job);
	}

}
