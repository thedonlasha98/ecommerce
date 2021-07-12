package com.ecommerce.web.jwt.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ECOM_PASSWORD_REQUESTS")
public class PasswordRequest {
    @Id
    @SequenceGenerator(name = "ECOM_SEQ", sequenceName = "ECOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_SEQ")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "HASH_VALUE", length = 100)
    private  String hashValue;
}