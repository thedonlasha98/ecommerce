package com.ecommerce.ecommerceWeb.ropository;

import java.util.List;
import java.util.Optional;

import com.ecommerce.ecommerceWeb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

    Optional<User> findByIdOrEmail(Long id, String email);
}
