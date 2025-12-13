package com.university.courseregistration.service;

import com.university.courseregistration.dto.EnrollmentCreatedEvent;
import com.university.courseregistration.dto.EnrollmentDTO;
import com.university.courseregistration.entity.Course;
import com.university.courseregistration.entity.Enrollment;
import com.university.courseregistration.entity.EnrollmentStatus;
import com.university.courseregistration.entity.Student;
import com.university.courseregistration.exception.EnrollmentException;
import com.university.courseregistration.exception.ResourceNotFoundException;
import com.university.courseregistration.kafka.KafkaProducerService;
import com.university.courseregistration.repository.CourseRepository;
import com.university.courseregistration.repository.EnrollmentRepository;
import com.university.courseregistration.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        log.info("Creating enrollment for student ID: {} and course ID: {}",
                enrollmentDTO.getStudentId(), enrollmentDTO.getCourseId());

        // Validate student exists
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with ID: " + enrollmentDTO.getStudentId()));

        // Validate course exists
        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with ID: " + enrollmentDTO.getCourseId()));

        // Check if already enrolled
        if (enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(
                enrollmentDTO.getStudentId(),
                enrollmentDTO.getCourseId(),
                EnrollmentStatus.ACTIVE)) {
            throw new EnrollmentException("Student is already enrolled in this course");
        }

        // Check course capacity
        if (course.getEnrolled() >= course.getCapacity()) {
            throw new EnrollmentException("Course is full. Cannot enroll more students.");
        }

        // Create enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(enrollmentDTO.getStudentId());
        enrollment.setCourseId(enrollmentDTO.getCourseId());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        Enrollment saved = enrollmentRepository.save(enrollment);

        // Increment course enrolled count
        course.setEnrolled(course.getEnrolled() + 1);
        courseRepository.save(course);

        log.info("Enrollment created successfully with ID: {}", saved.getId());

        // Send Kafka event
        EnrollmentCreatedEvent event = new EnrollmentCreatedEvent(
                saved.getId(),
                student.getId(),
                course.getId(),
                course.getName(),
                student.getName(),
                saved.getEnrolledAt()
        );
        kafkaProducerService.sendEnrollmentCreatedEvent(event);

        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public EnrollmentDTO getEnrollmentById(Long id) {
        log.info("Fetching enrollment with ID: {}", id);
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + id));
        return convertToDTO(enrollment);
    }

    @Transactional(readOnly = true)
    public List<EnrollmentDTO> getEnrollmentsByStudentId(Long studentId) {
        log.info("Fetching enrollments for student ID: {}", studentId);

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }

        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EnrollmentDTO> getEnrollmentsByCourseId(Long courseId) {
        log.info("Fetching enrollments for course ID: {}", courseId);

        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with ID: " + courseId);
        }

        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelEnrollment(Long id) {
        log.info("Cancelling enrollment with ID: {}", id);

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + id));

        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
            throw new EnrollmentException("Enrollment is already cancelled");
        }

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(enrollment);

        // Decrement course enrolled count
        Course course = courseRepository.findById(enrollment.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        course.setEnrolled(Math.max(0, course.getEnrolled() - 1));
        courseRepository.save(course);

        log.info("Enrollment cancelled successfully with ID: {}", id);
    }

    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setStudentId(enrollment.getStudentId());
        dto.setCourseId(enrollment.getCourseId());
        dto.setStatus(enrollment.getStatus());
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        return dto;
    }
}