package com.ecommerce.ecommerceWeb.domain;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ECOM_USERS")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "ECOM_USER_SEQ", sequenceName = "ECOM_USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_USER_SEQ")
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PIN")
    private String pin;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PASSWORD")
    private String password;

}