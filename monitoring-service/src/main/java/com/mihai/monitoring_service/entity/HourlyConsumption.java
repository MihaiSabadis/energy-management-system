package com.mihai.monitoring_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "hourly_consumption",
        uniqueConstraints = @UniqueConstraint(columnNames = {"device_id", "hour_start"}))
@Getter @Setter @NoArgsConstructor
public class HourlyConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "hour_start", nullable = false)
    private LocalDateTime hourStart;    // e.g. 2026-11-21T14:00

    @Column(nullable = false)
    private double totalKwh;

    public HourlyConsumption(UUID deviceId, LocalDateTime hourStart) {
        this.deviceId = deviceId;
        this.hourStart = hourStart;
        this.totalKwh = 0;
    }
}