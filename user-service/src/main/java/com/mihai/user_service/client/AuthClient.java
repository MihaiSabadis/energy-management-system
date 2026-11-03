package com.mihai.user_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class AuthClient {

    private final RestClient restClient;

    public AuthClient(@Value("${auth-service.url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    public void deleteCredentials(UUID userId) {
        restClient.delete()
                .uri("/api/auth/internal/users/{userId}", userId)
                .retrieve()
                .toBodilessEntity();
    }
}