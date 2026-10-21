package com.mihai.user_service.dto;

import java.util.UUID;

public record UserResponse(UUID id, String username, String fullName, String email, String address) { }