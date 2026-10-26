package com.mihai.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// public self-registration: implicitly will be client
public record RegisterRequest(
        @NotBlank @Size(max = 50) String username,
        @NotBlank @Size(min = 6) String password,
        @NotBlank String fullName,
        @NotBlank @Email String email,
        String address
) {}
