package com.mihai.monitoring_service.service;

import com.mihai.monitoring_service.dto.DeviceConsumption;
import com.mihai.monitoring_service.entity.HourlyConsumption;
import com.mihai.monitoring_service.entity.MonitoredDevice;
import com.mihai.monitoring_service.repository.HourlyConsumptionRepository;
import com.mihai.monitoring_service.repository.MonitoredDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsumptionService {

    private final MonitoredDeviceRepository deviceRepository;
    private final HourlyConsumptionRepository hourlyRepository;

    public List<DeviceConsumption> forUserAndDay(UUID userId, LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.atTime(23, 59, 59);

        List<DeviceConsumption> result = new ArrayList<>();
        for (MonitoredDevice device : deviceRepository.findByUserId(userId)) {
            List<Double> hourly = new ArrayList<>(Collections.nCopies(24, 0.0));
            for (HourlyConsumption h : hourlyRepository
                    .findByDeviceIdAndHourStartBetweenOrderByHourStart(device.getId(), from, to)) {
                hourly.set(h.getHourStart().getHour(), h.getTotalKwh());
            }
            result.add(new DeviceConsumption(device.getId(), device.getName(), hourly));
        }
        return result;
    }
}