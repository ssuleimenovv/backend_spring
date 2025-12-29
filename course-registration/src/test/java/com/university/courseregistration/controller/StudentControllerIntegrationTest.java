package com.university.courseregistration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.courseregistration.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCourses_NoAuth_Success() throws Exception {
        // Public endpoint - no authentication needed
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createStudent_AsAdmin_Success() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Integration Test Student");
        studentDTO.setEmail("integration@test.com");
        studentDTO.setStudentId("STU999");

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Integration Test Student"))
                .andExpect(jsonPath("$.email").value("integration@test.com"));
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void createStudent_AsStudent_Forbidden() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Test Student");
        studentDTO.setEmail("test@test.com");
        studentDTO.setStudentId("STU888");

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void createStudent_NoAuth_Unauthorized() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Test Student");
        studentDTO.setEmail("test@test.com");
        studentDTO.setStudentId("STU777");

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isUnauthorized());
    }
}