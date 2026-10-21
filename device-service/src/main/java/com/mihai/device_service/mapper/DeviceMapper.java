package com.mihai.device_service.mapper;

import com.mihai.device_service.dto.DeviceRequest;
import com.mihai.device_service.dto.DeviceResponse;
import com.mihai.device_service.entity.Device;

public final class DeviceMapper {

    private DeviceMapper() {}

    public static DeviceResponse toResponse(Device d) {
        return new DeviceResponse(d.getId(), d.getName(), d.getDescription(),
                d.getAddress(), d.getMaxConsumption(), d.getUserId());
    }

    public static Device toEntity(DeviceRequest req) {
        return Device.builder()
                .name(req.name())
                .description(req.description())
                .address(req.address())
                .maxConsumption(req.maxConsumption())
                .build();
    }

    public static void updateEntity(Device device, DeviceRequest req) {
        device.setName(req.name());
        device.setDescription(req.description());
        device.setAddress(req.address());
        device.setMaxConsumption(req.maxConsumption());
    }
}
