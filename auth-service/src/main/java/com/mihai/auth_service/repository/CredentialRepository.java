package com.mihai.auth_service.repository;

import com.mihai.auth_service.entity.Credential;
import com.mihai.auth_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {

    Optional<Credential> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByRole(Role role);
}
