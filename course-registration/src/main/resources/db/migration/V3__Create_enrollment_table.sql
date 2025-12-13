CREATE TABLE enrollments (
                             id BIGSERIAL PRIMARY KEY,
                             student_id BIGINT NOT NULL,
                             course_id BIGINT NOT NULL,
                             status VARCHAR(50) NOT NULL,
                             enrolled_at TIMESTAMP,
                             CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                             CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
                             CONSTRAINT unique_enrollment UNIQUE (student_id, course_id)
);