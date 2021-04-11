package com.ecommerce.ecommerceWeb.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long productId;

    private String product;

    private Long userId;

    private byte[] photo;

    private String price;

    private String status;

    private Long quantity;
}
