package com.mihai.user_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class DeviceClient {

    private final RestClient restClient;

    public DeviceClient(@Value("${device-service.url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    public void removeAssignmentsOfUser(UUID userId) {
        restClient.delete()
                .uri("/api/devices/assignments/user/{userId}", userId)
                .retrieve()
                .toBodilessEntity();
    }
}
