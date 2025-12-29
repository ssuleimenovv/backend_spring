package com.university.courseregistration.service;

import com.university.courseregistration.dto.StudentDTO;
import com.university.courseregistration.entity.Student;
import com.university.courseregistration.exception.DuplicateResourceException;
import com.university.courseregistration.exception.ResourceNotFoundException;
import com.university.courseregistration.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private StudentDTO studentDTO;
    private Student student;

    @BeforeEach
    void setUp() {
        studentDTO = new StudentDTO();
        studentDTO.setName("John Doe");
        studentDTO.setEmail("john@university.com");
        studentDTO.setStudentId("STU001");

        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john@university.com");
        student.setStudentId("STU001");
    }

    @Test
    void createStudent_Success() {
        // Given
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(false);
        when(studentRepository.existsByStudentId(studentDTO.getStudentId())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // When
        StudentDTO result = studentService.createStudent(studentDTO);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@university.com", result.getEmail());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void createStudent_DuplicateEmail_ThrowsException() {
        // Given
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> {
            studentService.createStudent(studentDTO);
        });
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void getStudentById_Success() {
        // Given
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // When
        StudentDTO result = studentService.getStudentById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void getStudentById_NotFound_ThrowsException() {
        // Given
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudentById(999L);
        });
    }

    @Test
    void deleteStudent_Success() {
        // Given
        when(studentRepository.existsById(1L)).thenReturn(true);

        // When
        studentService.deleteStudent(1L);

        // Then
        verify(studentRepository).deleteById(1L);
    }

    @Test
    void deleteStudent_NotFound_ThrowsException() {
        // Given
        when(studentRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.deleteStudent(999L);
        });
        verify(studentRepository, never()).deleteById(any());
    }
}