package com.university.notification.service;

import com.university.notification.dto.EnrollmentCreatedEvent;
import com.university.notification.model.Notification;
import com.university.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(EnrollmentCreatedEvent event) {
        log.info("Creating notification for enrollment: {}", event.getEnrollmentId());

        Notification notification = new Notification();
        notification.setEnrollmentId(event.getEnrollmentId());
        notification.setStudentId(event.getStudentId());
        notification.setStudentName(event.getStudentName());
        notification.setCourseName(event.getCourseName());
        notification.setMessage(String.format(
                "Dear %s, you have been successfully enrolled in %s",
                event.getStudentName(),
                event.getCourseName()
        ));
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus(Notification.NotificationStatus.SENT);

        Notification saved = notificationRepository.save(notification);
        
        log.info("Notification created successfully: ID={}", saved.getId());
        
        // Simulate sending notification (email/SMS)
        simulateSendNotification(saved);
        
        return saved;
    }

    private void simulateSendNotification(Notification notification) {
        log.info("========================================");
        log.info("📧 SENDING NOTIFICATION");
        log.info("========================================");
        log.info("To: {} (Student ID: {})", notification.getStudentName(), notification.getStudentId());
        log.info("Subject: Course Enrollment Confirmation");
        log.info("Message: {}", notification.getMessage());
        log.info("Course: {}", notification.getCourseName());
        log.info("Time: {}", notification.getCreatedAt());
        log.info("========================================");
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByStudentId(Long studentId) {
        return notificationRepository.findByStudentId(studentId);
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public long getNotificationCount() {
        return notificationRepository.count();
    }
}
