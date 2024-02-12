package com.nimesa.aws.service;

import java.util.List;

import com.nimesa.aws.model.S3Bucket;

import software.amazon.awssdk.services.s3.model.S3Object;

public interface S3BucketObjectService {

	void addS3BucketObjects(List<S3Object> s3Objects, S3Bucket bucket);

	Long countByBucketBucketName(String bucketName);

	List<String> getFileNamesFromBucketAndObjectKeyLike(String bucketName, String objectKey);

	List<String> findByBucketBucketName(String bucketName);
	
}
