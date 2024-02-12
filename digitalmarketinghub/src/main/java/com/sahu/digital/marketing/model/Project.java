package com.sahu.digital.marketing.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.sahu.digital.marketing.constants.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Project extends Auditable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 36)
	private String uuid;
	
	@Column(length = 50)
	private String name;
	
	private Boolean favorite;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Status status;

	@Column(name = "website_url")
	private String websiteURL;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_project", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;

}
