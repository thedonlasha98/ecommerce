package com.ecommerce.ecommerceWeb.domain;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ECOM_ACCOUNTS")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @SequenceGenerator(name = "ECOM_SEQ", sequenceName = "ECOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PIN")
    private String pin;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "BALANCE")
    private Double balance;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @OneToOne(optional=false)
    @JoinColumn(referencedColumnName="id", insertable=false, updatable=false)
    private User user;

    public Account(Long userId, String firstName, String lastName, String pin, String acctNo,Double balance, LocalDateTime regDate) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.acctNo = acctNo;
        this.balance = balance;
        this.regDate = regDate;
    }
}