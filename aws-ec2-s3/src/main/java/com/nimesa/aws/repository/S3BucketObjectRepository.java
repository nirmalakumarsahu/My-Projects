package com.nimesa.aws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nimesa.aws.model.S3BucketObject;

public interface S3BucketObjectRepository extends JpaRepository<S3BucketObject, Long> {

	Long countByBucketBucketName(String bucketName);

	@Query("SELECT s3object FROM S3BucketObject s3object WHERE s3object.bucket.bucketName = :bucketName AND s3object.objectKey LIKE %:objectKey%")
	List<S3BucketObject> findByBucketBucketNameAndObjectKeyLike(String bucketName, String objectKey);
	
	List<S3BucketObject> findByBucketBucketName(String bucketName);

}
