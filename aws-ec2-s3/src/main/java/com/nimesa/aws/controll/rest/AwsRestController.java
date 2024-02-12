package com.nimesa.aws.controll.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimesa.aws.constants.AWSConstants;
import com.nimesa.aws.constants.Status;
import com.nimesa.aws.model.Job;
import com.nimesa.aws.model.S3Bucket;
import com.nimesa.aws.service.EC2InstanceService;
import com.nimesa.aws.service.JobService;
import com.nimesa.aws.service.S3BucketObjectService;
import com.nimesa.aws.service.S3BucketSevice;
import com.nimesa.aws.util.AWSUtil;

import software.amazon.awssdk.services.s3.model.S3Object;

@RestController
@RequestMapping("/api/v1/aws")
public class AwsRestController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private AWSUtil awsUtil;

	@Autowired
	private JobService jobService;

	@Autowired
	private EC2InstanceService ec2InstanceService;

	@Autowired
	private S3BucketSevice s3BucketSevice;

	@Autowired
	private S3BucketObjectService s3BucketObjectService;

	@GetMapping("/discover-services")
	public ResponseEntity<?> discoverServices(@RequestParam(name = "services") List<String> services) {
		LOGGER.debug("inside discoverServices() method");
		LOGGER.info("Serivces - " + services);

		if(services.contains(AWSConstants.EC2) || services.contains(AWSConstants.S3)) {
			Job job = jobService.addJob(services);
			Thread mainThead = new Thread(() -> {
				try {
					Thread.sleep(60000);
					if (services.contains(AWSConstants.EC2)) {
						Thread thread = new Thread(() -> {
							List<String> instances = awsUtil.collectAWSEC2Instances();
							ec2InstanceService.saveEC2Instances(instances);
						});
						thread.start();
					}
					Thread.sleep(60000);
					if (services.contains(AWSConstants.S3)) {
						Thread thread = new Thread(() -> {
							List<String> bucketNames = awsUtil.collectAWSS3Buckets();
							s3BucketSevice.saveS3Buckets(bucketNames);
						});
						thread.start();
					}
					job.setStatus(Status.SUCCESS);
				} catch (Exception e) {
					job.setStatus(Status.FAILED);
				}
				jobService.updateJob(job);
			});
			mainThead.start();

			return ResponseEntity.ok(job.getId());
		}
		
		return ResponseEntity.badRequest().body("Given services are not allowed and supported!");
	}

	@GetMapping("/get-job-result")
	public ResponseEntity<?> getJobResult(@RequestParam Long jobId) {
		if (jobId != null) {
			Job job = jobService.findById(jobId);
			if (job != null)
				return ResponseEntity.ok(job.getStatus());

			return ResponseEntity.badRequest().body("Data not found!");
		}

		return ResponseEntity.badRequest().body("Id must not null!");

	}

	@GetMapping("/get-discovery-result")
	public ResponseEntity<?> getDiscoveryResult(@RequestParam String service) {
		if (service != null) {

			List<String> serviceList = null;
			if (service.equalsIgnoreCase(AWSConstants.EC2)) {
				serviceList = ec2InstanceService.findAllInstanceIds();
			} else if (service.equalsIgnoreCase(AWSConstants.S3)) {
				serviceList = s3BucketSevice.findAllBucketNames();
			} else {
				return ResponseEntity.badRequest().body(service + " Service Not allowed!");
			}

			return ResponseEntity.ok(serviceList);
		}

		return ResponseEntity.badRequest().body("Service must not null!");
	}

	@GetMapping("/get-s3-bucket-objects")
	public ResponseEntity<?> getS3BucketObjects(String bucketName) {
		if (bucketName != null) {
			S3Bucket s3Bucket = s3BucketSevice.findByBucketName(bucketName);
			if (s3Bucket != null) {
				Job job = jobService.addJob(bucketName);
				try {
					Thread thread = new Thread(() -> {
						
						try {
							Thread.sleep(60000);
							List<S3Object> s3Objects = awsUtil.collectAWSS3BucketObjects(bucketName);
							s3BucketObjectService.addS3BucketObjects(s3Objects, s3Bucket);
							job.setStatus(Status.SUCCESS);
						} catch (Exception e) {
							job.setStatus(Status.FAILED);
						}

						jobService.updateJob(job);
					});
					thread.start();

				} catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}

				return ResponseEntity.ok(job.getId());
			}

			return ResponseEntity.badRequest().body("Bucket Not available!");
		}

		return ResponseEntity.badRequest().body("Bucket Must Not be null!");
	}
	
	@GetMapping("/get-s3-bucket-objects-details")
	public ResponseEntity<?> getS3BucketObjectsDetails(String bucketName) {
		if (bucketName != null) {
			Boolean isExist = s3BucketSevice.existsByBucketName(bucketName);
			if (isExist) {
				List<String> bucketObjets = s3BucketObjectService.findByBucketBucketName(bucketName);
				return ResponseEntity.ok(bucketObjets);
			}

			return ResponseEntity.badRequest().body("Bucket not Exist!");
		}

		return ResponseEntity.badRequest().body("Bucket Must Not be null!");
	}

	@GetMapping("/get-s3-bucket-object-count")
	public ResponseEntity<?> getS3BucketObjectCount(String bucketName) {
		if (bucketName != null) {
			Boolean isExist = s3BucketSevice.existsByBucketName(bucketName);
			if (isExist) {
				Long s3ObjectCount = s3BucketObjectService.countByBucketBucketName(bucketName);
				return ResponseEntity.ok(s3ObjectCount);
			}

			return ResponseEntity.badRequest().body("Bucket not Exist!");
		}

		return ResponseEntity.badRequest().body("Bucket Must Not be null!");
	}

	@GetMapping("/get-s3-bucket-object-like")
	public ResponseEntity<?> getS3BucketObjectlike(String bucketName, String pattern) {
		if (bucketName != null) {
			Boolean isExist = s3BucketSevice.existsByBucketName(bucketName);
			if (isExist) {
				List<String> fileNames = s3BucketObjectService.getFileNamesFromBucketAndObjectKeyLike(bucketName,
						pattern);
				return ResponseEntity.ok(fileNames);
			}

			return ResponseEntity.badRequest().body("Bucket not Exist!");
		}

		return ResponseEntity.badRequest().body("Bucket Must Not be null!");
	}

}
