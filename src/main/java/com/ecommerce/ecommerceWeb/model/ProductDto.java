package com.ecommerce.ecommerceWeb.model;


import com.ecommerce.ecommerceWeb.domain.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long productId;

    private String product;

    private String productName;

    private Long userId;

    private byte[] photo;

    private Double price;

    private String status;

    private Long quantity;

    public static ProductDto transformProducts(Product product){
        return ProductDto.builder()
                .productId(product.getId())
                .product(product.getProduct())
                .productName(product.getProductName())
                .userId(product.getUserId())
                .photo(product.getPhoto())
                .price(product.getPrice())
                .status(product.getStatus())
                .quantity(product.getQuantity())
                .build();
    }

}
