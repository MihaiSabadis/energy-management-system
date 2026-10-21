package com.mihai.device_service.controller;

import com.mihai.device_service.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.mihai.device_service.dto.DeviceRequest;
import com.mihai.device_service.dto.DeviceResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Tag(name = "Devices", description = "CRUD operations on devices and device-user assignments")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    @Operation(summary = "List all devices")
    public List<DeviceResponse> getAll() {
        return deviceService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a device by id")
    public DeviceResponse getById(@PathVariable UUID id) {
        return deviceService.findById(id);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "List the devices assigned to a user")
    public List<DeviceResponse> getByUser(@PathVariable UUID userId) {
        return deviceService.findByUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a device")
    public DeviceResponse create(@Valid @RequestBody DeviceRequest req) {
        return deviceService.create(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a device")
    public DeviceResponse update(@PathVariable UUID id, @Valid @RequestBody DeviceRequest req) {
        return deviceService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a device")
    public void delete(@PathVariable UUID id) {
        deviceService.delete(id);
    }

    @PutMapping("/{id}/user/{userId}")
    @Operation(summary = "Assign a device to a user")
    public DeviceResponse assign(@PathVariable UUID id, @PathVariable UUID userId) {
        return deviceService.assign(id, userId);
    }

    @DeleteMapping("/{id}/user")
    @Operation(summary = "Unassign a device from its user")
    public DeviceResponse unassign(@PathVariable UUID id) {
        return deviceService.unassign(id);
    }

    @DeleteMapping("/assignments/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Internal: remove all device assignments of a deleted user")
    public void removeAssignmentsOfUser(@PathVariable UUID userId) {
        deviceService.removeAssignmentsOfUser(userId);
    }
}
