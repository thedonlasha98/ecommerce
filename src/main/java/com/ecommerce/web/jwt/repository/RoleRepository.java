package com.ecommerce.web.jwt.repository;

import java.util.Optional;

import com.ecommerce.web.jwt.domain.ERole;
import com.ecommerce.web.jwt.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
