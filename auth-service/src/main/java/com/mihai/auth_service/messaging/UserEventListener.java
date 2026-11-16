package com.mihai.auth_service.messaging;

import com.mihai.auth_service.config.RabbitConfig;
import com.mihai.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {

    private final AuthService authService;

    @RabbitListener(queues = RabbitConfig.SYNC_QUEUE)
    public void onUserEvent(UserEvent event) {
        log.info("Received sync event: {}", event);
        if (UserEvent.DELETED.equals(event.type())) {
            authService.deleteCredentials(event.userId());
        }
    }
}