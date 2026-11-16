package com.mihai.user_service.messaging;

import com.mihai.user_service.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void userCreated(UUID userId, String username) {
        publish("user.created", new UserEvent(UserEvent.CREATED, userId, username));
    }

    public void userDeleted(UUID userId, String username) {
        publish("user.deleted", new UserEvent(UserEvent.DELETED, userId, username));
    }

    private void publish(String routingKey, UserEvent event) {
        rabbitTemplate.convertAndSend(RabbitConfig.SYNC_EXCHANGE, routingKey, event);
        log.info("Published {} -> {}: {}", RabbitConfig.SYNC_EXCHANGE, routingKey, event);
    }
}