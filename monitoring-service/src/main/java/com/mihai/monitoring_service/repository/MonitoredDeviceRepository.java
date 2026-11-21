package com.mihai.monitoring_service.repository;

import com.mihai.monitoring_service.entity.MonitoredDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface MonitoredDeviceRepository extends JpaRepository<MonitoredDevice, UUID> {
    List<MonitoredDevice> findByUserId(UUID userId);
}
