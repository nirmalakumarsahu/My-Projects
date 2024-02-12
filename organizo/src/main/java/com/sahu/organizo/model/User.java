package com.sahu.organizo.model;

import java.util.Date;
import java.util.Set;

import com.sahu.organizo.constants.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
		@UniqueConstraint(columnNames = {"user_name", "email"})
})
public class User extends Auditable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String firstName;

	@Column(length = 50)
	private String middleName;

	@Column(length = 50)
	private String lastName;

	private String email;

	private String userName;
	
	private String displayName;

	private String password;

	private String photoPath;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Status status;

	@Column(length = 6)
	private String otp;
	
	private Date otpValidedTime;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

}
