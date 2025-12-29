package com.university.notification.kafka;

import com.university.notification.dto.EnrollmentCreatedEvent;
import com.university.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentEventConsumer.class);
    private final NotificationService notificationService;

    public EnrollmentEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "enrollment-created",
            groupId = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeEnrollmentEvent(EnrollmentCreatedEvent event) {
        log.info("========================================");
        log.info("📨 RECEIVED KAFKA EVENT");
        log.info("========================================");
        log.info("Event Type: Enrollment Created");
        log.info("Enrollment ID: {}", event.getEnrollmentId());
        log.info("Student: {} (ID: {})", event.getStudentName(), event.getStudentId());
        log.info("Course: {} (ID: {})", event.getCourseName(), event.getCourseId());
        log.info("Enrolled At: {}", event.getEnrolledAt());
        log.info("========================================");

        try {
            // Create notification from event
            notificationService.createNotification(event);
            log.info("✅ Event processed successfully");
        } catch (Exception e) {
            log.error("❌ Error processing enrollment event: {}", e.getMessage(), e);
        }
    }
}
