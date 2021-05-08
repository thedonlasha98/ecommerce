package com.ecommerce.ecommerceWeb.service;

import com.ecommerce.ecommerceWeb.domain.Product;
import com.ecommerce.ecommerceWeb.model.BuyProductDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    String addProduct(ProductDto productDto);

    void activateProducts(Long id, Long userId);

    void closeProducts(Long id, Long userId);

    void deleteProducts(Long id, Long userId);

    String modifyProduct(ProductDto productDto);

    List<Product> getProducts();

    void buyProduct(BuyProductDto buyProductDto);
}
