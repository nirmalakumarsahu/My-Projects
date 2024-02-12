package com.nimesa.aws.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

@Component
public class AWSUtil {

	// @Value("${aws.access.key.id}")
	private String accessKeyId = "AKIAX5XSI5UTUOMGM5F3";

	// @Value("${aws.secret.access.key}")
	private String secretAccessKey = "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";

	AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);;

	public List<String> collectAWSEC2Instances() {
		Ec2Client ec2Client = Ec2Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
				.region(Region.AP_SOUTH_1).build();

		List<String> ec2Instance = new ArrayList<>();
		DescribeInstancesResponse ec2Response = ec2Client.describeInstances();
		for (Reservation reservation : ec2Response.reservations()) {
			for (Instance instance : reservation.instances()) {
				ec2Instance.add(instance.instanceId());
			}
		}

		return ec2Instance;
	}

	public List<String> collectAWSS3Buckets() {
		S3Client s3Client = getS3Client();

		List<Bucket> buckets = s3Client.listBuckets().buckets();
		List<String> s3Buckets = new ArrayList<>();
		for (Bucket bucket : buckets) {
			s3Buckets.add(bucket.name());
		}

		return s3Buckets;
	}

	public List<S3Object> collectAWSS3BucketObjects(String bucketName) throws Exception {
		S3Client s3Client = getS3Client();

		ListObjectsV2Response s3Response = s3Client
				.listObjectsV2(ListObjectsV2Request.builder().bucket(bucketName).build());

		List<S3Object> s3Objects = new ArrayList<>();
		for (S3Object s3Object : s3Response.contents()) {
			s3Objects.add(s3Object);
		}

		return s3Objects;
	}

	private S3Client getS3Client() {
		return S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
				.region(Region.AP_SOUTH_1).build();
	}

}
