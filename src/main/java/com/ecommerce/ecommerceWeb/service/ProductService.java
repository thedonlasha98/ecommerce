package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.domain.Product;
import com.ecommerce.ecommerceWeb.model.BuyProductDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface ProductService {
    String addProduct(ProductDto productDto);

    void activateProduct(Long id, Long userId);

    void closeProduct(Long id, Long userId);

    void deleteProduct(Long id, Long userId);

    String modifyProduct(ProductDto productDto);

    List<Product> getProducts();

    @Transactional
    void buyProduct(BuyProductDto buyProductDto);
}
