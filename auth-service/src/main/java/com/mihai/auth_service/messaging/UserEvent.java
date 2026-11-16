package com.mihai.auth_service.messaging;

import java.util.UUID;

public record UserEvent(String type, UUID userId, String username) {
    public static final String CREATED = "USER_CREATED";
    public static final String DELETED = "USER_DELETED";
}