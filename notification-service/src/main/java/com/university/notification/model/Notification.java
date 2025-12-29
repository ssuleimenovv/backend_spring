package com.university.notification.model;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private Long enrollmentId;
    private Long studentId;
    private String studentName;
    private String courseName;
    private String message;
    private LocalDateTime createdAt;
    private NotificationStatus status;

    public enum NotificationStatus {
        PENDING, SENT, FAILED
    }

    public Notification() {
    }

    public Notification(Long id, Long enrollmentId, Long studentId, String studentName, 
                       String courseName, String message, LocalDateTime createdAt, NotificationStatus status) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseName = courseName;
        this.message = message;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }
}
