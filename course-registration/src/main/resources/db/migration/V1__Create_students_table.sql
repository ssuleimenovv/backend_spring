CREATE TABLE students (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          student_id VARCHAR(50) NOT NULL UNIQUE,
                          created_at TIMESTAMP
);