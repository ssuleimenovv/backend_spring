package com.university.courseregistration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentCreatedEvent {
    private Long enrollmentId;
    private Long studentId;
    private Long courseId;
    private String courseName;
    private String studentName;
    private LocalDateTime enrolledAt;
}