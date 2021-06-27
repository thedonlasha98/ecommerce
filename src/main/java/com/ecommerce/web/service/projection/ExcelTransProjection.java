package com.ecommerce.web.service.projection;

import java.math.BigDecimal;

public interface ExcelTransProjection {

    String getProduct();

    String getProductName();

    BigDecimal getAmount();

    String getBuyerEmail();

    String getBuyerPin();

}
