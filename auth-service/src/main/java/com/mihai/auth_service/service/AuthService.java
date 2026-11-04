package com.mihai.auth_service.service;

import com.mihai.auth_service.repository.CredentialRepository;
import com.mihai.auth_service.client.UserClient;
import com.mihai.auth_service.dto.*;
import com.mihai.auth_service.entity.*;
import com.mihai.auth_service.exception.*;
import com.mihai.auth_service.security.AccessPolicy;
import com.mihai.auth_service.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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
    private final JwtUtil jwtUtil;
    private final AccessPolicy accessPolicy;

    @Transactional
    public LoginResponse register(RegisterRequest req) {
        Credential cred = createAccount(req.username(), req.password(), req.fullName(),
                req.email(), req.address(), Role.CLIENT);
        return toLoginResponse(cred);            // registering also logs you in
    }

    @Transactional
    public AccountResponse createUser(CreateUserRequest req) {
        Credential cred = createAccount(req.username(), req.password(), req.fullName(),
                req.email(), req.address(), req.role());
        return new AccountResponse(cred.getId(), cred.getUsername(), cred.getRole());
    }

    public LoginResponse login(LoginRequest req) {
        Credential cred = credentialRepository.findByUsername(req.username())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
        if (!passwordEncoder.matches(req.password(), cred.getPasswordHash()))
            throw new UnauthorizedException("Invalid username or password");
        return toLoginResponse(cred);
    }

    private LoginResponse toLoginResponse(Credential cred) {
        return new LoginResponse(jwtUtil.generateToken(cred),
                cred.getId(), cred.getUsername(), cred.getRole());
    }

    private Credential createAccount(String username, String password, String fullName,
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
        return credentialRepository.save(cred);
    }

    public ValidateResponse validate(String authHeader, String method, String uri) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Missing or malformed Authorization header");

        ValidateResponse identity;
        try {
            Claims claims = jwtUtil.parse(authHeader.substring("Bearer ".length()));
            identity = new ValidateResponse(
                    UUID.fromString(claims.getSubject()),
                    claims.get("username", String.class),
                    Role.valueOf(claims.get("role", String.class)));
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("Invalid or expired token");
        }

        // called directly (from Swagger): only check the token
        if (method == null && uri == null) return identity;

        if (!accessPolicy.isAllowed(identity.role(), identity.userId(), method, uri))
            throw new ForbiddenException("Not allowed: " + method + " " + uri);

        return identity;
    }

    @Transactional
    public void deleteCredentials(UUID userId) {
        credentialRepository.deleteById(userId);
    }
}