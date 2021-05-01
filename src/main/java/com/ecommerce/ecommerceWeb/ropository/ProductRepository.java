package com.ecommerce.ecommerceWeb.ropository;

import com.ecommerce.ecommerceWeb.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByProductAndUserId(String product, Long userId);

    Product findByIdAndUserId(Long id, Long userId);

    Product getProductById(Long id);

    List<Product> findAllByStatusOrderByIdDesc(String status);

}
