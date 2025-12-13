package com.university.courseregistration.controller;

import com.university.courseregistration.dto.EnrollmentDTO;
import com.university.courseregistration.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment Management", description = "APIs for managing course enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "Create a new enrollment (register student for a course)")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO created = enrollmentService.createEnrollment(enrollmentDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get enrollment by ID")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all enrollments for a student")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get all enrollments for a course")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByCourseId(@PathVariable Long courseId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel enrollment")
    public ResponseEntity<Void> cancelEnrollment(@PathVariable Long id) {
        enrollmentService.cancelEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}