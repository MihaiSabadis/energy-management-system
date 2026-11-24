package com.mihai.monitoring_service.messaging;

import com.mihai.monitoring_service.config.RabbitConfig;
import com.mihai.monitoring_service.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MeasurementListener {

    private final MeasurementService measurementService;

    @RabbitListener(queues = RabbitConfig.MEASUREMENTS_QUEUE)
    public void onMeasurement(Measurement measurement) {
        log.info("Received measurement: {}", measurement);   // "display messages … in Console"
        measurementService.process(measurement);
    }
}