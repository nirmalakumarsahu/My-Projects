package com.nimesa.aws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimesa.aws.model.EC2Instance;

public interface EC2InstanceRepository extends JpaRepository<EC2Instance, Long> {

}
