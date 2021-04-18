package com.ecommerce.ecommerceWeb.ropository;

import java.util.Optional;

import com.ecommerce.ecommerceWeb.domain.ERole;
import com.ecommerce.ecommerceWeb.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
