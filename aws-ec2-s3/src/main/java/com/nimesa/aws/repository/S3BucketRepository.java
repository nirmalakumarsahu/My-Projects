package com.nimesa.aws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimesa.aws.model.S3Bucket;

public interface S3BucketRepository extends JpaRepository<S3Bucket, Long> {

	S3Bucket findByBucketName(String bucketName);
	
	Boolean existsByBucketName(String bucketName);
	
}
