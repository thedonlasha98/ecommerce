package com.ecommerce.web.model;


import com.ecommerce.web.domain.Product;
import lombok.*;

import java.math.BigDecimal;

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

    private BigDecimal price;

    private String status;

    private Integer quantity;

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
