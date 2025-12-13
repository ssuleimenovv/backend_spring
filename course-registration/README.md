# University Course Registration System

A microservice-based REST API for managing students, courses, and enrollments.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **PostgreSQL** - Database
- **Apache Kafka** - Event streaming
- **Flyway** - Database migrations
- **Swagger/OpenAPI** - API documentation
- **Docker & Docker Compose** - Containerization

## Features

- ✅ CRUD operations for Students, Courses, and Enrollments
- ✅ PostgreSQL database with Flyway migrations
- ✅ Kafka producer/consumer for enrollment events
- ✅ REST API with proper validation and error handling
- ✅ Swagger UI for API documentation
- ✅ Dockerized application

## Prerequisites

- Java 17+
- Maven 3.6+
- Docker & Docker Compose

## Running Locally

### Option 1: With Docker Compose (Recommended)

1. Clone the repository
2. Run the entire stack:
```bash
docker-compose up -d
```

3. Access the application:
    - API: http://localhost:8080
    - Swagger UI: http://localhost:8080/swagger-ui/index.html

### Option 2: Manual Setup

1. Start PostgreSQL and Kafka:
```bash
docker-compose up -d postgres zookeeper kafka
```

2. Build and run the application:
```bash
./mvnw clean install
./mvnw spring-boot:run
```

## API Endpoints

### Students
- `POST /api/students` - Create a student
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

### Courses
- `POST /api/courses` - Create a course
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course by ID
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course

### Enrollments
- `POST /api/enrollments` - Enroll student in course
- `GET /api/enrollments/{id}` - Get enrollment by ID
- `GET /api/enrollments/student/{studentId}` - Get student's enrollments
- `GET /api/enrollments/course/{courseId}` - Get course enrollments
- `DELETE /api/enrollments/{id}` - Cancel enrollment

## Testing the API

### 1. Create a Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@university.com",
    "studentId": "STU001"
  }'
```

### 2. Create a Course
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Introduction to Computer Science",
    "code": "CS101",
    "capacity": 30,
    "instructor": "Dr. Smith"
  }'
```

### 3. Enroll Student in Course
```bash
curl -X POST http://localhost:8080/api/enrollments \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "courseId": 1
  }'
```

## Kafka Events

When a student enrolls in a course, an `EnrollmentCreatedEvent` is published to Kafka topic `enrollment-created`. The consumer logs the event with student and course details.

## Database Schema

The application uses 3 main tables:
- `students` - Student information
- `courses` - Course information
- `enrollments` - Student-Course enrollments

All schema changes are managed via Flyway migrations in `src/main/resources/db/migration/`.

## Project Structure
```
src/main/java/com/university/courseregistration/
├── controller/       # REST controllers
├── service/         # Business logic
├── repository/      # Data access layer
├── entity/          # JPA entities
├── dto/             # Data transfer objects
├── config/          # Configuration classes
├── exception/       # Exception handling
└── kafka/           # Kafka producer/consumer
```

## Stopping the Application
```bash
docker-compose down
```

To remove volumes:
```bash
docker-compose down -v
```

## License

MIT