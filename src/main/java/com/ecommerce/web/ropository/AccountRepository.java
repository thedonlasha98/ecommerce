package com.ecommerce.web.ropository;

import com.ecommerce.web.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account,Long> {
    Account findByPin(String pin);

    Account getAccountByUserId(Long userId);
}
