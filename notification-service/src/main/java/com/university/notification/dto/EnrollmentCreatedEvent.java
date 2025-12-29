package com.university.notification.dto;

import java.time.LocalDateTime;

public class EnrollmentCreatedEvent {
    private Long enrollmentId;
    private Long studentId;
    private Long courseId;
    private String courseName;
    private String studentName;
    private LocalDateTime enrolledAt;

    public EnrollmentCreatedEvent() {
    }

    public EnrollmentCreatedEvent(Long enrollmentId, Long studentId, Long courseId, 
                                 String courseName, String studentName, LocalDateTime enrolledAt) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentName = studentName;
        this.enrolledAt = enrolledAt;
    }

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }
}
