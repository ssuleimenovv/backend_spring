package com.university.courseregistration.kafka;

import com.university.courseregistration.config.KafkaTopicConfig;
import com.university.courseregistration.dto.EnrollmentCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = KafkaTopicConfig.ENROLLMENT_CREATED_TOPIC, groupId = "course-registration-group")
    public void consumeEnrollmentCreatedEvent(EnrollmentCreatedEvent event) {
        log.info("========================================");
        log.info("📧 ENROLLMENT NOTIFICATION");
        log.info("========================================");
        log.info("Enrollment ID: {}", event.getEnrollmentId());
        log.info("Student: {} (ID: {})", event.getStudentName(), event.getStudentId());
        log.info("Course: {} (ID: {})", event.getCourseName(), event.getCourseId());
        log.info("Enrolled At: {}", event.getEnrolledAt());
        log.info("========================================");

        // Here you can add logic like:
        // - Send email notification
        // - Update statistics
        // - Trigger other business processes
    }
}