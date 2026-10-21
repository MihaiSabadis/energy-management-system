package com.mihai.device_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DeviceRequest(
        @NotBlank String name,
        String description,
        String address,
        @NotNull @Positive Double maxConsumption
) {}
