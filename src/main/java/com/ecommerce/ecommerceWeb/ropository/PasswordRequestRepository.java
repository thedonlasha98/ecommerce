package com.ecommerce.ecommerceWeb.ropository;

import com.ecommerce.ecommerceWeb.domain.PasswordRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRequestRepository extends JpaRepository<PasswordRequest,Long> {
    PasswordRequest getPasswordRequestByHashValue(String hash);
}
