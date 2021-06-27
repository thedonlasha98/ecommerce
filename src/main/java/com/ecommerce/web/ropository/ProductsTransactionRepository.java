package com.ecommerce.web.ropository;

import com.ecommerce.web.domain.ProductsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsTransactionRepository extends JpaRepository<ProductsTransaction,Long> {
}
