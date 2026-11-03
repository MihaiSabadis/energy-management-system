package com.mihai.auth_service.dto;

import com.mihai.auth_service.entity.Role;

import java.util.UUID;

public record AccountResponse(UUID userId, String username, Role role) {}
