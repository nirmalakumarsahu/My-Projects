package com.sahu.um.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
public class Permission extends Auditable<Long> implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String name;
	
	private String description;
	
	private Boolean active;
}
