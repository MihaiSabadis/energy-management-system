package com.mihai.auth_service.service;

import com.mihai.auth_service.repository.CredentialRepository;
import com.mihai.auth_service.client.UserClient;
import com.mihai.auth_service.dto.*;
import com.mihai.auth_service.entity.*;
import com.mihai.auth_service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserClient userClient;

    @Transactional
    public LoginResponse register(RegisterRequest req) {
        return createAccount(req.username(), req.password(), req.fullName(),
                req.email(), req.address(), Role.CLIENT);
    }

    @Transactional
    public LoginResponse createUser(CreateUserRequest req) {
        return createAccount(req.username(), req.password(), req.fullName(),
                req.email(), req.address(), req.role());
    }

    public LoginResponse login(LoginRequest req) {
        Credential cred = credentialRepository.findByUsername(req.username())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
        if (!passwordEncoder.matches(req.password(), cred.getPasswordHash()))
            throw new UnauthorizedException("Invalid username or password");
        return new LoginResponse(cred.getId(), cred.getUsername(), cred.getRole());
    }

    private LoginResponse createAccount(String username, String password, String fullName,
                                        String email, String address, Role role) {
        if (credentialRepository.existsByUsername(username))
            throw new ConflictException("Username already taken");

        UUID id = userClient.createProfile(username, fullName, email, address);

        Credential cred = Credential.builder()
                .id(id)
                .username(username)
                .passwordHash(passwordEncoder.encode(password))
                .role(role)
                .build();
        credentialRepository.save(cred);
        return new LoginResponse(id, username, role);
    }
}