package com.mihai.device_service.service;

import com.mihai.device_service.dto.DeviceRequest;
import com.mihai.device_service.dto.DeviceResponse;
import com.mihai.device_service.entity.Device;
import com.mihai.device_service.entity.UserRef;
import com.mihai.device_service.exception.NotFoundException;
import com.mihai.device_service.mapper.DeviceMapper;
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
        return DeviceMapper.toResponse(deviceRepository.save(DeviceMapper.toEntity(req)));
    }

    @Transactional
    public DeviceResponse update(UUID id, DeviceRequest req) {
        Device device = getOrThrow(id);
        DeviceMapper.updateEntity(device, req);
        return DeviceMapper.toResponse(device);
    }

    @Transactional
    public void delete(UUID id) {
        deviceRepository.delete(getOrThrow(id));
    }

    @Transactional
    public DeviceResponse assign(UUID deviceId, UUID userId) {
        Device device = getOrThrow(deviceId);
        UserRef user = userRefRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId + " not found"));
        device.setUser(user);
        return DeviceMapper.toResponse(device);
    }

    @Transactional
    public DeviceResponse unassign(UUID deviceId) {
        Device device = getOrThrow(deviceId);
        device.setUser(null);
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
        deviceRepository.findByUserId(userId).forEach(d -> d.setUser(null));
        userRefRepository.deleteById(userId);
    }

    private Device getOrThrow(UUID id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Device " + id + " not found"));
    }
}