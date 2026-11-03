package com.mihai.device_service.dto;

import java.util.UUID;

public record DeviceResponse(
        UUID id,
        String name,
        String description,
        Double maxConsumption,
        UUID userId
) {}