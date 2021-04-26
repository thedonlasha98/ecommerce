package com.ecommerce.ecommerceWeb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long productId;

    private String product;

    private Long userId;

    private byte[] photo;

    private Double price;

    private String status;

    private Long quantity;
}
