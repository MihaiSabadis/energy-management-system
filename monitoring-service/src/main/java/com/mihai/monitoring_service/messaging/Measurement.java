package com.mihai.monitoring_service.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record Measurement(
        @JsonProperty("timestamp") LocalDateTime timestamp,
        @JsonProperty("device_id") UUID deviceId,
        @JsonProperty("measurement_value") double measurementValue
) {}