package com.ecommerce.ecommerceWeb.domain;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ECOM_ACCOUNTS")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PIN")
    private String pin;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;
}