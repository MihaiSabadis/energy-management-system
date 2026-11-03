package com.mihai.auth_service.dto;

import com.mihai.auth_service.entity.Role;

import java.util.UUID;

public record LoginResponse(String token, UUID userId, String username, Role role) {}
