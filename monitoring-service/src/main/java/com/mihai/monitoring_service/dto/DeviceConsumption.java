package com.mihai.monitoring_service.dto;

import java.util.List;
import java.util.UUID;

public record DeviceConsumption(UUID deviceId, String deviceName, List<Double> hourly) {}