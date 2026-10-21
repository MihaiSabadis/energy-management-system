package com.mihai.device_service.dto;

import java.util.UUID;

public record DeviceResponse(
        UUID id,
        String name,
        String description,
        String address,
        Double maxConsumption,
        UUID userId
) {}