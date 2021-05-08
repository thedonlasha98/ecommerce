package com.ecommerce.ecommerceWeb.domain;

import com.ecommerce.ecommerceWeb.service.projections.ProductProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ECOM_PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(name = "ECOM_SEQ", sequenceName = "ECOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "PRODUCT_NAME")
    private String productName;

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

}