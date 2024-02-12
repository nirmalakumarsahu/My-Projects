package com.sahu.organizo.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

	@CreatedBy
	protected U createdBy;

	@CreatedDate
	protected Date createdAt;

	@LastModifiedBy
	protected U updatedBy;

	@LastModifiedDate
	protected Date updatedAt;

	@Column(length = 36)
	private String uuid;
	
	private Boolean active;
	
}
