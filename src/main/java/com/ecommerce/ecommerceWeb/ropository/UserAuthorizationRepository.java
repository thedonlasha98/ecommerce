package com.ecommerce.ecommerceWeb.ropository;

import com.ecommerce.ecommerceWeb.domain.UserAuthorization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorizationRepository extends CrudRepository<UserAuthorization,Long> {
    UserAuthorization findFirstByUserIdOrderByAuthDateDesc(Long userId);
}
