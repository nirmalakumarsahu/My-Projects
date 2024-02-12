package com.nimesa.aws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimesa.aws.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
