package com.mihai.auth_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String SYNC_EXCHANGE = "sync.exchange";
    public static final String SYNC_QUEUE = "auth.sync";

    @Bean
    public TopicExchange syncExchange() {
        return new TopicExchange(SYNC_EXCHANGE, true, false);
    }

    @Bean
    public Queue syncQueue() {
        return new Queue(SYNC_QUEUE, true);
    }

    @Bean
    public Binding userDeletedBinding(Queue syncQueue, TopicExchange syncExchange) {
        return BindingBuilder.bind(syncQueue).to(syncExchange).with("user.deleted");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}