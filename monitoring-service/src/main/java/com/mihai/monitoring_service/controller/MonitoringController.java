package com.mihai.monitoring_service.controller;

import com.mihai.monitoring_service.dto.DeviceConsumption;
import com.mihai.monitoring_service.service.ConsumptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
@Tag(name = "Monitoring", description = "Hourly energy consumption")
public class MonitoringController {

    private final ConsumptionService consumptionService;

    @GetMapping("/users/{userId}/consumption")
    @Operation(summary = "Hourly consumption of a user's devices for one day (24 values per device)")
    public List<DeviceConsumption> getConsumption(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return consumptionService.forUserAndDay(userId, date);
    }
}