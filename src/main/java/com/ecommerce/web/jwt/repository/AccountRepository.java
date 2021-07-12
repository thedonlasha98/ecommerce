package com.ecommerce.web.jwt.repository;

import com.ecommerce.web.jwt.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account,Long> {
    Account findByPin(String pin);

    Account getAccountByUserId(Long userId);
}
