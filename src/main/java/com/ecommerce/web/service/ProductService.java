package com.ecommerce.web.service;

import com.ecommerce.web.domain.Product;
import com.ecommerce.web.model.BuyProductDto;
import com.ecommerce.web.model.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    String addProduct(ProductDto productDto);

    void activateProduct(Long id, Long userId);

    void closeProduct(Long id, Long userId);

    void deleteProduct(Long id, Long userId);

    String modifyProduct(ProductDto productDto);

    List<Product> getProducts();

    void buyProduct(BuyProductDto buyProductDto) ;
}
