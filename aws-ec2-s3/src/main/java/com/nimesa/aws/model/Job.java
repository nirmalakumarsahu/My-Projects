package com.nimesa.aws.model;

import com.nimesa.aws.constants.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq")
	@SequenceGenerator(name = "job_seq", sequenceName = "job_seq", allocationSize = 1, initialValue = 1001)
	private Long id;

	private String serivces;

	@Enumerated(EnumType.STRING)
	private Status status;
}
