package com.mihai.device_service.messaging;

import com.mihai.device_service.config.RabbitConfig;
import com.mihai.device_service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {

    private final DeviceService deviceService;

    @RabbitListener(queues = RabbitConfig.SYNC_QUEUE)
    public void onUserEvent(UserEvent event) {
        log.info("Received sync event: {}", event);

        if (event.type().equals(UserEvent.CREATED)) {
            deviceService.syncUserCreated(event.userId());
        } else {
            log.info("Ignoring event type {}", event.type());
        }
    }
}