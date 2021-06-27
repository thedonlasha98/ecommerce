package com.ecommerce.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyProductDto {

    private Long userId;

    private Long productId;

    private Integer quantity;

    private Long cardNo;

    private String expDate;

    private int cvv;

}
