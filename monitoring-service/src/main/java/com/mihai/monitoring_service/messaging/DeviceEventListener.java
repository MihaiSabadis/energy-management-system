package com.mihai.monitoring_service.messaging;

import com.mihai.monitoring_service.config.RabbitConfig;
import com.mihai.monitoring_service.service.DeviceSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceEventListener {

    private final DeviceSyncService deviceSyncService;

    @RabbitListener(queues = RabbitConfig.SYNC_QUEUE)
    public void onDeviceEvent(DeviceEvent event) {
        log.info("Received sync event: {}", event);

        switch (event.type()) {
            case DeviceEvent.CREATED, DeviceEvent.UPDATED -> deviceSyncService.upsert(event);
            case DeviceEvent.DELETED -> deviceSyncService.delete(event.deviceId());
            default -> log.info("Ignoring event type {}", event.type());
        }
    }
}