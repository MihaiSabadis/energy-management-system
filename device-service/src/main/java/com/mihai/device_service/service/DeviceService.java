package com.mihai.device_service.service;

import com.mihai.device_service.dto.DeviceRequest;
import com.mihai.device_service.dto.DeviceResponse;
import com.mihai.device_service.entity.Device;
import com.mihai.device_service.entity.UserRef;
import com.mihai.device_service.exception.NotFoundException;
import com.mihai.device_service.mapper.DeviceMapper;
import com.mihai.device_service.messaging.DeviceEventPublisher;
import com.mihai.device_service.repository.DeviceRepository;
import com.mihai.device_service.repository.UserRefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRefRepository userRefRepository;
    private final DeviceEventPublisher deviceEventPublisher;

    public List<DeviceResponse> findAll() {
        return deviceRepository.findAll().stream().map(DeviceMapper::toResponse).toList();
    }

    public DeviceResponse findById(UUID id) {
        return DeviceMapper.toResponse(getOrThrow(id));
    }

    public List<DeviceResponse> findByUser(UUID userId) {
        return deviceRepository.findByUserId(userId).stream().map(DeviceMapper::toResponse).toList();
    }

    @Transactional
    public DeviceResponse create(DeviceRequest req) {

        Device saved = deviceRepository.save(DeviceMapper.toEntity(req));
        deviceEventPublisher.deviceCreated(saved);
        return DeviceMapper.toResponse(saved);
    }

    @Transactional
    public DeviceResponse update(UUID id, DeviceRequest req) {
        Device device = getOrThrow(id);
        DeviceMapper.updateEntity(device, req);
        deviceEventPublisher.deviceUpdated(device);
        return DeviceMapper.toResponse(device);
    }

    @Transactional
    public void delete(UUID id) {
        Device device = getOrThrow(id);
        deviceRepository.delete(device);
        deviceEventPublisher.deviceDeleted(device);
    }

    @Transactional
    public DeviceResponse assign(UUID deviceId, UUID userId) {
        Device device = getOrThrow(deviceId);
        UserRef user = userRefRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId + " not found"));
        device.setUser(user);
        deviceEventPublisher.deviceUpdated(device);
        return DeviceMapper.toResponse(device);
    }

    @Transactional
    public DeviceResponse unassign(UUID deviceId) {
        Device device = getOrThrow(deviceId);
        device.setUser(null);
        deviceEventPublisher.deviceUpdated(device);
        return DeviceMapper.toResponse(device);
    }

    @Transactional
    public void syncUserCreated(UUID userId) {
        if (!userRefRepository.existsById(userId)) {
            userRefRepository.save(new UserRef(userId));
        }
    }

    @Transactional
    public void syncUserDeleted(UUID userId) {
        deviceRepository.findByUserId(userId).forEach(d -> {
                d.setUser(null);
                deviceEventPublisher.deviceUpdated(d);
        });
        userRefRepository.deleteById(userId);
    }

    private Device getOrThrow(UUID id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Device " + id + " not found"));
    }
}