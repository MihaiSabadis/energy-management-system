package com.mihai.device_service.messaging;

import com.mihai.device_service.config.RabbitConfig;
import com.mihai.device_service.entity.Device;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void deviceCreated(Device d) { publish("device.created", toEvent(DeviceEvent.CREATED, d)); }
    public void deviceUpdated(Device d) { publish("device.updated", toEvent(DeviceEvent.UPDATED, d)); }
    public void deviceDeleted(Device d) { publish("device.deleted", toEvent(DeviceEvent.DELETED, d)); }

    private DeviceEvent toEvent(String type, Device d) {
        return new DeviceEvent(type, d.getId(), d.getName(), d.getMaxConsumption(),
                d.getUser() != null ? d.getUser().getId() : null);
    }

    private void publish(String routingKey, DeviceEvent event) {
        rabbitTemplate.convertAndSend(RabbitConfig.SYNC_EXCHANGE, routingKey, event);
        log.info("Published {} -> {}: {}", RabbitConfig.SYNC_EXCHANGE, routingKey, event);
    }
}