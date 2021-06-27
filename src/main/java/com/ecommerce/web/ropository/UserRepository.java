package com.ecommerce.web.ropository;

import java.util.Optional;

import com.ecommerce.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	User getUserByEmail(String email);

	User findByEmailAndPassword(String email, String password);

    Optional<User> findByIdOrEmail(Long id, String email);

	Long getIdByEmail(String email);
}
