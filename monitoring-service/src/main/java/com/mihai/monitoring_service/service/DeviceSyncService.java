package com.mihai.monitoring_service.service;

import com.mihai.monitoring_service.entity.MonitoredDevice;
import com.mihai.monitoring_service.messaging.DeviceEvent;
import com.mihai.monitoring_service.repository.MonitoredDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceSyncService {

    private final MonitoredDeviceRepository deviceRepository;

    @Transactional
    public void upsert(DeviceEvent e) {
        MonitoredDevice device = deviceRepository.findById(e.deviceId())
                .orElseGet(MonitoredDevice::new);
        device.setId(e.deviceId());
        device.setName(e.name());
        device.setMaxConsumption(e.maxConsumption());
        device.setUserId(e.userId());
        deviceRepository.save(device);
    }

    @Transactional
    public void delete(UUID deviceId) {
        deviceRepository.deleteById(deviceId);
    }
}