package com.university.courseregistration.service;

import com.university.courseregistration.dto.CourseDTO;
import com.university.courseregistration.entity.Course;
import com.university.courseregistration.exception.DuplicateResourceException;
import com.university.courseregistration.exception.ResourceNotFoundException;
import com.university.courseregistration.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public CourseDTO createCourse(CourseDTO courseDTO) {
        log.info("Creating course with code: {}", courseDTO.getCode());

        if (courseRepository.existsByCode(courseDTO.getCode())) {
            throw new DuplicateResourceException("Course with code " + courseDTO.getCode() + " already exists");
        }

        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setCode(courseDTO.getCode());
        course.setCapacity(courseDTO.getCapacity());
        course.setEnrolled(0);
        course.setInstructor(courseDTO.getInstructor());

        Course saved = courseRepository.save(course);
        log.info("Course created successfully with ID: {}", saved.getId());

        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public CourseDTO getCourseById(Long id) {
        log.info("Fetching course with ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
        return convertToDTO(course);
    }

    @Transactional(readOnly = true)
    public List<CourseDTO> getAllCourses() {
        log.info("Fetching all courses");
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        log.info("Updating course with ID: {}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));

        course.setName(courseDTO.getName());
        course.setInstructor(courseDTO.getInstructor());
        course.setCapacity(courseDTO.getCapacity());

        Course updated = courseRepository.save(course);
        log.info("Course updated successfully with ID: {}", updated.getId());

        return convertToDTO(updated);
    }

    @Transactional
    public void deleteCourse(Long id) {
        log.info("Deleting course with ID: {}", id);

        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with ID: " + id);
        }

        courseRepository.deleteById(id);
//        courseRepository.deleteById(id);
        log.info("Course deleted successfully with ID: {}", id);
    }

    @Transactional
    public void incrementEnrolled(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));
        course.setEnrolled(course.getEnrolled() + 1);
        courseRepository.save(course);
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setCode(course.getCode());
        dto.setCapacity(course.getCapacity());
        dto.setEnrolled(course.getEnrolled());
        dto.setInstructor(course.getInstructor());
        return dto;
    }
}