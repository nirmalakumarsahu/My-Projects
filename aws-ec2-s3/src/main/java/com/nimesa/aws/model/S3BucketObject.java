package com.nimesa.aws.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "s3_bucket_object")
public class S3BucketObject {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s3_bucket_object_seq")
	@SequenceGenerator(name = "s3_bucket_object_seq", sequenceName = "s3_bucket_object_seq", allocationSize = 1, initialValue = 1001)
	private Long id;
	
	@Column(name = "object_key")
	@NonNull
	private String objectKey;
	
	@ManyToOne
    @JoinColumn(name = "bucket_id")
    private S3Bucket bucket;
}
