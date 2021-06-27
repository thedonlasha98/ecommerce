package com.ecommerce.web.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ECOM_PRODUCTS_TRANSACTIONS")
@Getter
@Setter
@NoArgsConstructor
public class ProductsTransaction {
    @Id
    @SequenceGenerator(name = "ECOM_SEQ", sequenceName = "ECOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "FROM_ACCT_ID")
    private Long fromAcctId;

    @Column(name = "TO_ACCT_ID")
    private Long toAcctId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "COM_AMOUNT")
    private BigDecimal comAmount;

    @Column(name = "INP_SYSDATE")
    private LocalDateTime inpSysdate;

}
