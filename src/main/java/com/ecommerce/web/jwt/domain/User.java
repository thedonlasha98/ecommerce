package com.ecommerce.web.jwt.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(	name = "ECOM_USERS")
public class User {
	@Id
	@SequenceGenerator(name = "ECOM_USER_SEQ", sequenceName = "ECOM_USER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_USER_SEQ")
	@Column(name="ID")
	private Long id;

	@Size(max = 50)
	@Email
	@Column(name="EMAIL")
	private String email;

	@Size(max = 120)
	@Column(name="PASSWORD")
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "ecom_users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
