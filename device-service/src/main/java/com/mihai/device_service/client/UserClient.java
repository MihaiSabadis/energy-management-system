package com.mihai.device_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class UserClient {

    private final RestClient restClient;

    public UserClient(@Value("${user-service.url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    public boolean userExists(UUID userId) {
        try {
            restClient.get().uri("/api/users/{id}", userId).retrieve().toBodilessEntity();
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
