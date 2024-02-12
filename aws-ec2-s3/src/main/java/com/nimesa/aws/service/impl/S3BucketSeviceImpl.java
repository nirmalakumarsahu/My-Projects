package com.nimesa.aws.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimesa.aws.model.S3Bucket;
import com.nimesa.aws.repository.S3BucketRepository;
import com.nimesa.aws.service.S3BucketSevice;

@Service
public class S3BucketSeviceImpl implements S3BucketSevice {

	@Autowired
	private S3BucketRepository bucketRepository;

	@Override
	public void saveS3Buckets(List<String> bucketNames) {
		List<S3Bucket> s3Buckets = bucketNames.stream().map(S3Bucket::new).collect(Collectors.toList());
		bucketRepository.saveAll(s3Buckets);
	}

	@Override
	public List<String> findAllBucketNames() {
		List<S3Bucket> bucketNames = bucketRepository.findAll();
		return bucketNames != null ? bucketNames.stream().map(S3Bucket::getBucketName).collect(Collectors.toList())
				: null;
	}

	@Override
	public S3Bucket findByBucketName(String name) {
		return bucketRepository.findByBucketName(name);
	}

	@Override
	public Boolean existsByBucketName(String bucketName) {
		return bucketRepository.existsByBucketName(bucketName);
	}

}
