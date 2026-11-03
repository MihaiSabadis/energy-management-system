package com.mihai.auth_service.controller;

import com.mihai.auth_service.dto.*;
import com.mihai.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Registration, login, account creation and token validation")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new client account and log in")
    public LoginResponse register(@Valid @RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    @Operation(summary = "Log in and receive a JWT")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Admin: create an account with a chosen role")
    public AccountResponse createUser(@Valid @RequestBody CreateUserRequest req) {
        return authService.createUser(req);
    }

    @GetMapping("/validate")
    @Operation(summary = "Validate a JWT and check access (called by the API gateway)")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ValidateResponse> validate(
            @Parameter(hidden = true)
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @Parameter(hidden = true)
            @RequestHeader(value = "X-Forwarded-Method", required = false) String method,
            @Parameter(hidden = true)
            @RequestHeader(value = "X-Forwarded-Uri", required = false) String uri) {

        ValidateResponse v = authService.validate(authHeader, method, uri);
        return ResponseEntity.ok()
                .header("X-User-Id", v.userId().toString())
                .header("X-User-Role", v.role().name())
                .body(v);
    }

    @DeleteMapping("/internal/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Internal: delete the credentials of a deleted user")
    public void deleteCredentials(@PathVariable UUID userId) {
        authService.deleteCredentials(userId);
    }
}
