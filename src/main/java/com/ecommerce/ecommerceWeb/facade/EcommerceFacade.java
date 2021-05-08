package com.ecommerce.ecommerceWeb.facade;

import com.ecommerce.ecommerceWeb.domain.Product;
import com.ecommerce.ecommerceWeb.model.BuyProductDto;
import com.ecommerce.ecommerceWeb.model.ProductDto;
import com.ecommerce.ecommerceWeb.service.ProductService;
import com.ecommerce.ecommerceWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EcommerceFacade
 *
 * @blame Android Team
 */
@Component
public class EcommerceFacade {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public String addProducts(ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    public void activateProducts(Long id, Long userId) {
        productService.activateProducts(id, userId);
    }

    public void closeProducts(Long id, Long userId) {
        productService.closeProducts(id, userId);
    }

    public void deleteProducts(Long id, Long userId) {
        productService.deleteProducts(id, userId);
    }

    public String modifyProduct(ProductDto productDto) {
        return productService.modifyProduct(productDto);
    }


    public List<ProductDto> getProducts() {
        List<Product> products = productService.getProducts();

        return products.stream()
                .map(ProductDto::transformProducts)
                .collect(Collectors.toList());
    }

    public void buyProduct(BuyProductDto buyProductDto) {
        productService.buyProduct(buyProductDto);
    }
}
