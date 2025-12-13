package com.university.courseregistration.repository;

import com.university.courseregistration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    Optional<Student> findByStudentId(String studentId);
    boolean existsByEmail(String email);
    boolean existsByStudentId(String studentId);
}