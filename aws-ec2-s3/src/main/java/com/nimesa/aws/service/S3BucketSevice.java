package com.nimesa.aws.service;

import java.util.List;

import com.nimesa.aws.model.S3Bucket;

public interface S3BucketSevice {
	
	void saveS3Buckets(List<String> bucketNames);
	
	List<String> findAllBucketNames();
	
	S3Bucket findByBucketName(String name);
	
	Boolean existsByBucketName(String bucketName);
	
}
