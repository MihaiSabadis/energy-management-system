package com.mihai.monitoring_service.repository;

import com.mihai.monitoring_service.entity.HourlyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HourlyConsumptionRepository extends JpaRepository<HourlyConsumption, UUID> {

    Optional<HourlyConsumption> findByDeviceIdAndHourStart(UUID deviceId, LocalDateTime hourStart);

    // for the chart, when selecting the day
    List<HourlyConsumption> findByDeviceIdAndHourStartBetweenOrderByHourStart(
            UUID deviceId, LocalDateTime from, LocalDateTime to);
}