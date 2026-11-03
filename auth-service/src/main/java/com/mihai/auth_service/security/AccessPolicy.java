package com.mihai.auth_service.security;

import com.mihai.auth_service.entity.Role;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccessPolicy {

    public boolean isAllowed(Role role, UUID userId, String method, String uri) {
        // ADMIN: has full access,can pass
        if (role == Role.ADMIN) return true;

        // CLIENT: read-only access to their own data
        if (!"GET".equals(method) || uri == null) return false;
        String path = uri.split("\\?")[0];          // ignore query string

        return path.equals("/api/users/" + userId)
                || path.equals("/api/devices/user/" + userId);
    }
}