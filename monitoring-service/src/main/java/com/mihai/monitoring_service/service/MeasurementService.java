package com.mihai.monitoring_service.service;

import com.mihai.monitoring_service.entity.HourlyConsumption;
import com.mihai.monitoring_service.messaging.Measurement;
import com.mihai.monitoring_service.repository.HourlyConsumptionRepository;
import com.mihai.monitoring_service.repository.MonitoredDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeasurementService {

    private final MonitoredDeviceRepository deviceRepository;
    private final HourlyConsumptionRepository hourlyRepository;

    @Transactional
    public void process(Measurement m) {
        if (!deviceRepository.existsById(m.deviceId())) {
            log.warn("Ignoring measurement for unknown device {}", m.deviceId());
            return;
        }

        LocalDateTime hourStart = m.timestamp().truncatedTo(ChronoUnit.HOURS);

        HourlyConsumption hour = hourlyRepository
                .findByDeviceIdAndHourStart(m.deviceId(), hourStart)
                .orElseGet(() -> new HourlyConsumption(m.deviceId(), hourStart));

        hour.setTotalKwh(hour.getTotalKwh() + m.measurementValue());
        hourlyRepository.save(hour);

        log.info("Device {} | hour {} | total now {} kWh",
                m.deviceId(), hourStart, String.format("%.3f", hour.getTotalKwh()));
    }
}