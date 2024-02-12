package com.sahu.organizo.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"name", "priority"})
})
public class Role  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50)
	private String name;
	
	@Column(length = 50)
	private String label;

	private Integer priority;
	
	@Column(length = 36)
	private String uuid;
	
	private Boolean active;
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

}
