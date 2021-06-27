package com.ecommerce.web.ropository;

import java.util.Optional;

import com.ecommerce.web.domain.ERole;
import com.ecommerce.web.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
