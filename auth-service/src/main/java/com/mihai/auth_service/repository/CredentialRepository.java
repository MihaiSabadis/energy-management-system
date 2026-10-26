package com.mihai.auth_service.repository;

import com.mihai.auth_service.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {

    Optional<Credential> findByUsername(String username);
    boolean existsByUsername(String username);
}
