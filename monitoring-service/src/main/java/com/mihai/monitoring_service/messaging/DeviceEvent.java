package com.mihai.monitoring_service.messaging;

import java.util.UUID;

public record DeviceEvent(String type, UUID deviceId, String name,
                          Double maxConsumption, UUID userId) {
    public static final String CREATED = "DEVICE_CREATED";
    public static final String UPDATED = "DEVICE_UPDATED";
    public static final String DELETED = "DEVICE_DELETED";
}