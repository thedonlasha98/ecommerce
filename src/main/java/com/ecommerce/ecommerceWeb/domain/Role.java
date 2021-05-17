package com.ecommerce.ecommerceWeb.domain;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@SequenceGenerator(name = "ECOM_SEQ", sequenceName = "ECOM_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_SEQ")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "NAME", length = 20)
	private ERole name;

	public Role() {

	}

	public Role(ERole name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}