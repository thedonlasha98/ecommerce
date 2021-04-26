package com.ecommerce.ecommerceWeb.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ECOM_PRODUCTS_HIST")
@Getter
@Setter
@NoArgsConstructor
public class ProductHist {
    @Id
    @SequenceGenerator(name = "ECOM_SEQ", sequenceName = "ECOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "USER_ID")
    private Long userId;

    @Lob
    @Column(name = "PHOTO", columnDefinition="BLOB")
    private byte[] photo;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "QUANTITY")
    private Long quantity;

    @Column(name = "EVENT")
    private String event;

    @Column(name = "INP_SYSDATE")
    private LocalDateTime inpSysDate;

}