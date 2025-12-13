-- Insert test students
INSERT INTO students (name, email, student_id, created_at) VALUES
                                                               ('Alice Johnson', 'alice.johnson@university.com', 'STU001', CURRENT_TIMESTAMP),
                                                               ('Bob Smith', 'bob.smith@university.com', 'STU002', CURRENT_TIMESTAMP),
                                                               ('Charlie Brown', 'charlie.brown@university.com', 'STU003', CURRENT_TIMESTAMP);

-- Insert test courses
INSERT INTO courses (name, code, capacity, enrolled, instructor) VALUES
                                                                     ('Introduction to Computer Science', 'CS101', 30, 0, 'Dr. Smith'),
                                                                     ('Data Structures and Algorithms', 'CS201', 25, 0, 'Dr. Johnson'),
                                                                     ('Database Systems', 'CS301', 20, 0, 'Dr. Williams');