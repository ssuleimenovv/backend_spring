package com.university.courseregistration.service;

import com.university.courseregistration.dto.StudentDTO;
import com.university.courseregistration.entity.Student;
import com.university.courseregistration.exception.DuplicateResourceException;
import com.university.courseregistration.exception.ResourceNotFoundException;
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
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("Creating student with email: {}", studentDTO.getEmail());

        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentDTO.getEmail() + " already exists");
        }

        if (studentRepository.existsByStudentId(studentDTO.getStudentId())) {
            throw new DuplicateResourceException("Student with ID " + studentDTO.getStudentId() + " already exists");
        }

        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setStudentId(studentDTO.getStudentId());

        Student saved = studentRepository.save(student);
        log.info("Student created successfully with ID: {}", saved.getId());

        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long id) {
        log.info("Fetching student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return convertToDTO(student);
    }

    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        log.info("Updating student with ID: {}", id);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        if (!student.getEmail().equals(studentDTO.getEmail()) &&
                studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentDTO.getEmail() + " already exists");
        }

        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());

        Student updated = studentRepository.save(student);
        log.info("Student updated successfully with ID: {}", updated.getId());

        return convertToDTO(updated);
    }

    @Transactional
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);

        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with ID: " + id);
        }

        studentRepository.deleteById(id);
        log.info("Student deleted successfully with ID: {}", id);
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setStudentId(student.getStudentId());
        return dto;
    }
}