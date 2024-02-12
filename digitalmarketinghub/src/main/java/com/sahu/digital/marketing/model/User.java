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
public class User extends Auditable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 36)
	private String uuid;
	
	@Column(length = 50)
	private String firstName;
	
	@Column(length = 50)
	private String middleName;
	
	@Column(length = 50)
	private String lastName;
	
	private String email;
	
	private String userName;
	
	@Column(length = 20)
	private Long phoneNo;
	
	private String password;
	
	private String photoPath;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Status status;

	@Column(length = 36)
	private String resetPasswordToken;

	@Column(length = 36)
	private String activationCode;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	@ManyToMany(mappedBy = "users")
	private List<Project> projects;

}
