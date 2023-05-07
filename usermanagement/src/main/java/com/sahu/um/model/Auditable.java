package com.sahu.um.model;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<User> {
	@CreatedBy
	protected User createdBy;
	
	@CreatedDate
	//@Temporal(TemporalType.TIMESTAMP)
	protected Date createdAt;
	
	@LastModifiedBy
	protected User updatedBy;
	
	@LastModifiedDate
	//@Temporal(TemporalType.TIMESTAMP)
	protected Date updatedAt;
}
