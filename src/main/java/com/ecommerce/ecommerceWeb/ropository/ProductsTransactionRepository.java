package com.ecommerce.ecommerceWeb.ropository;

import com.ecommerce.ecommerceWeb.domain.ProductsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsTransactionRepository extends JpaRepository<ProductsTransaction,Long> {
}
