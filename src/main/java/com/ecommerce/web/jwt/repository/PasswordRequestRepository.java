package com.ecommerce.web.jwt.repository;

import com.ecommerce.web.jwt.domain.PasswordRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRequestRepository extends JpaRepository<PasswordRequest,Long> {
    PasswordRequest getPasswordRequestByHashValue(String hash);
}
