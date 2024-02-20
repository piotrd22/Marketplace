package com.example.marketplace.repositories;

import com.example.marketplace.enums.RoleName;
import com.example.marketplace.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
    Boolean existsByName(RoleName name);
}
