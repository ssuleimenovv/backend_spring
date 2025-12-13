package com.university.courseregistration.kafka;

import com.university.courseregistration.config.KafkaTopicConfig;
import com.university.courseregistration.dto.EnrollmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, EnrollmentCreatedEvent> kafkaTemplate;

    public void sendEnrollmentCreatedEvent(EnrollmentCreatedEvent event) {
        log.info("Sending enrollment created event: {}", event);
        kafkaTemplate.send(KafkaTopicConfig.ENROLLMENT_CREATED_TOPIC, event);
        log.info("Enrollment created event sent successfully");
    }
}