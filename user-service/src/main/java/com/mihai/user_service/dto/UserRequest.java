package com.mihai.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public record UserRequest(
    @NotBlank @Size(max = 50) String username,
    @NotBlank String fullName,
    @NotBlank @Email String email,
    @NotBlank String address
) {}
