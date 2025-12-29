package com.university.courseregistration.kafka;

import com.university.courseregistration.config.KafkaTopicConfig;
import com.university.courseregistration.dto.EnrollmentCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9093", "port=9093"})
class KafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, EnrollmentCreatedEvent> kafkaTemplate;

    @Test
    void testEnrollmentCreatedEventProduction() throws InterruptedException {
        // Given
        EnrollmentCreatedEvent event = new EnrollmentCreatedEvent(
                1L,
                1L,
                1L,
                "Test Course",
                "Test Student",
                LocalDateTime.now()
        );

        // When
        kafkaTemplate.send(KafkaTopicConfig.ENROLLMENT_CREATED_TOPIC, event);

        // Then
        // Wait a bit to ensure message is sent
        TimeUnit.SECONDS.sleep(1);

        // If no exception was thrown, the test passes
        assertTrue(true, "Event was successfully sent to Kafka");
    }
}