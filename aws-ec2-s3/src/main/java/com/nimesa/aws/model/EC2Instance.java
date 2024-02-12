package com.nimesa.aws.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "ec2_instance", uniqueConstraints = { @UniqueConstraint(columnNames = { "instance_id" }) })
public class EC2Instance {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ec2_instance_seq")
	@SequenceGenerator(name = "ec2_instance_seq", sequenceName = "ec2_instance_seq", allocationSize = 1, initialValue = 1001)
	private Long id;

	@Column(name = "instance_id")
	@NonNull
	private String instanceId;
}
