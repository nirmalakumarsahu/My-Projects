package com.nimesa.aws.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "s3_bucket", uniqueConstraints = { @UniqueConstraint(columnNames = { "bucket_name" }) })
public class S3Bucket {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s3_bucket_seq")
	@SequenceGenerator(name = "s3_bucket_seq", sequenceName = "s3_bucket_seq", allocationSize = 1, initialValue = 1001)
	private Long id;

	@Column(name = "bucket_name")
	@NonNull
	private String bucketName;

	@OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL)
	private List<S3BucketObject> bucketObjects;
}
