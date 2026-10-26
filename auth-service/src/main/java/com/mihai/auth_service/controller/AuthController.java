package com.mihai.auth_service.controller;

import com.mihai.auth_service.dto.CreateUserRequest;
import com.mihai.auth_service.dto.LoginRequest;
import com.mihai.auth_service.dto.LoginResponse;
import com.mihai.auth_service.dto.RegisterRequest;
import com.mihai.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Registration, login and account creation")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new client account")
    public LoginResponse register(@Valid @RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    @Operation(summary = "Log in with username and password")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Admin: create an account with a chosen role")
    public LoginResponse createUser(@Valid @RequestBody CreateUserRequest req) {
        return authService.createUser(req);
    }
}
