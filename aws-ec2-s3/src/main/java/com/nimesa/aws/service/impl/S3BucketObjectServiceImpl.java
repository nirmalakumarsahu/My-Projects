package com.nimesa.aws.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimesa.aws.model.S3Bucket;
import com.nimesa.aws.model.S3BucketObject;
import com.nimesa.aws.repository.S3BucketObjectRepository;
import com.nimesa.aws.service.S3BucketObjectService;

import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class S3BucketObjectServiceImpl implements S3BucketObjectService {

	@Autowired
	private S3BucketObjectRepository s3BucketObjectRepository;

	@Override
	public void addS3BucketObjects(List<S3Object> s3Objects, S3Bucket bucket) {
		List<S3BucketObject> s3BucketObjects = s3Objects.stream().map(s3Object -> {
			S3BucketObject s3BucketObject = new S3BucketObject();
			s3BucketObject.setObjectKey(s3Object.key());
			s3BucketObject.setBucket(bucket);
			return s3BucketObject;
		}).collect(Collectors.toList());

		s3BucketObjectRepository.saveAll(s3BucketObjects);
	}

	@Override
	public Long countByBucketBucketName(String bucketName) {
		return s3BucketObjectRepository.countByBucketBucketName(bucketName);
	}

	@Override
	public List<String> getFileNamesFromBucketAndObjectKeyLike(String bucketName, String objectKey) {
		List<S3BucketObject> s3BucketObjects = s3BucketObjectRepository
				.findByBucketBucketNameAndObjectKeyLike(bucketName, objectKey);
		return s3BucketObjects != null
				? s3BucketObjects.stream().map(S3BucketObject::getObjectKey).collect(Collectors.toList())
				: null;
	}

	@Override
	public List<String> findByBucketBucketName(String bucketName) {
		List<S3BucketObject> s3BucketObjects = s3BucketObjectRepository.findByBucketBucketName(bucketName);
		return s3BucketObjects != null
				? s3BucketObjects.stream().map(S3BucketObject::getObjectKey).collect(Collectors.toList())
				: null;
	}

}
