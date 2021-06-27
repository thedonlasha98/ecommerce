package com.ecommerce.web.ropository;

import com.ecommerce.web.domain.PasswordRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRequestRepository extends JpaRepository<PasswordRequest,Long> {
    PasswordRequest getPasswordRequestByHashValue(String hash);
}
