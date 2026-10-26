package com.mihai.auth_service.dto;

import com.mihai.auth_service.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// admin creating an account: can choose the role
public record CreateUserRequest(
        @NotBlank @Size(max = 50) String username,
        @NotBlank @Size(min = 6) String password,
        @NotBlank String fullName,
        @NotBlank @Email String email,
        String address,
        @NotNull Role role
) {}