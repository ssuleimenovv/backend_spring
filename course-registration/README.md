# 🎓 Course Registration System

A comprehensive microservices-based course registration system built with Spring Boot, featuring OAuth2 authentication, event-driven architecture, and containerized deployment.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [Security](#security)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Event Flow](#event-flow)

---

## 🎯 Overview

The Course Registration System is a modern, production-ready application that allows students to enroll in university courses while providing administrators with full management capabilities. The system implements industry best practices including microservices architecture, OAuth2 authentication, event-driven communication, and comprehensive testing.

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Spring Boot** | 3.5.8 | Application framework |
| **PostgreSQL** | 15 | Relational database |
| **Apache Kafka** | 7.5.0 | Event streaming platform |
| **Keycloak** | 23.0.0 | Identity and access management |
| **Docker** | Latest | Containerization |
| **Flyway** | 11.7.2 | Database migrations |
| **Swagger/OpenAPI** | 3.0 | API documentation |
| **JUnit & Mockito** | Latest | Testing framework |

---

## ✨ Features

### Core Functionality
- ✅ **Student Management**: CRUD operations for student records
- ✅ **Course Management**: Create, update, and delete courses
- ✅ **Enrollment System**: Students can enroll in and drop courses
- ✅ **Capacity Management**: Automatic enrollment tracking and capacity limits

### Security
- ✅ **OAuth2 Authentication**: JWT-based authentication via Keycloak
- ✅ **Role-Based Access Control**: Separate permissions for students and administrators
- ✅ **Public Endpoints**: Unauthenticated access to course listings

### Event-Driven Architecture
- ✅ **Kafka Integration**: Asynchronous event processing
- ✅ **Enrollment Events**: Real-time notifications when students enroll
- ✅ **Scalable Processing**: Decoupled producer-consumer pattern

### DevOps
- ✅ **Docker Compose**: Single-command deployment
- ✅ **Health Checks**: Automated service health monitoring
- ✅ **Database Migrations**: Version-controlled schema changes
- ✅ **Comprehensive Testing**: Unit, integration, and Kafka tests

---

## 🏗 Architecture

```
┌─────────────┐
│   Client    │
│  (Browser)  │
└──────┬──────┘
       │
       │ HTTP/REST + JWT
       ▼
┌──────────────────────────────────────┐
│     Spring Boot Application          │
│  ┌────────────────────────────────┐  │
│  │   REST Controllers             │  │
│  │   (Student, Course, Enrollment)│  │
│  └────────────┬───────────────────┘  │
│               │                       │
│  ┌────────────▼───────────────────┐  │
│  │   Service Layer                │  │
│  │   (Business Logic)             │  │
│  └────────────┬───────────────────┘  │
│               │                       │
│  ┌────────────▼───────────────────┐  │
│  │   Repository Layer             │  │
│  │   (JPA/Hibernate)              │  │
│  └────────────┬───────────────────┘  │
│               │                       │
│  ┌────────────▼───────────────────┐  │
│  │   Kafka Producer               │  │
│  │   (Event Publishing)           │  │
│  └────────────┬───────────────────┘  │
└───────────────┼───────────────────────┘
                │
       ┌────────┴────────┐
       │                 │
       ▼                 ▼
┌─────────────┐   ┌─────────────┐
│ PostgreSQL  │   │    Kafka    │
│  Database   │   │   Broker    │
└─────────────┘   └──────┬──────┘
                         │
                         ▼
                  ┌─────────────┐
                  │    Kafka    │
                  │  Consumer   │
                  └─────────────┘

Authentication Flow:
┌─────────┐      ┌──────────┐      ┌──────────┐
│ Client  │─────▶│ Keycloak │─────▶│   API    │
│         │◀─────│          │      │          │
└─────────┘ JWT  └──────────┘      └──────────┘
```

---

## 📦 Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **Docker & Docker Compose**
- **PostgreSQL 15** (for local development)
- **Git**

---

## 🚀 Quick Start

### Option 1: Run with Docker (Recommended)

1. **Clone the repository**
```bash
git clone <https://github.com/ssuleimenovv/backend_spring>
cd course-registration
```

2. **Start all services**
```bash
docker-compose up -d
```

3. **Wait for services to be healthy** (~90 seconds)
```bash
docker-compose ps
```

4. **Configure Keycloak** (first time only)
   - Open http://localhost:8180
   - Login: `admin` / `admin`
   - Follow the setup guide in `docs/KEYCLOAK_SETUP.md`

5. **Access the application**
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - Keycloak: http://localhost:8180

---

### Option 2: Run Locally (Development)

1. **Start infrastructure services**
```bash
docker-compose up -d postgres kafka zookeeper keycloak
```

2. **Configure Keycloak** (see above)

3. **Run the application**
```bash
mvn spring-boot:run
```

4. **Application will start on** http://localhost:8080

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Swagger UI
Interactive API documentation available at:
```
http://localhost:8080/swagger-ui.html
```

### Key Endpoints

#### Public Endpoints (No Authentication)
```http
GET /api/courses              # List all courses
```

#### Student Endpoints (Requires STUDENT role)
```http
POST   /api/enrollments                      # Enroll in a course
GET    /api/enrollments/student/{studentId}  # Get student's enrollments
DELETE /api/enrollments/{id}                 # Drop a course
GET    /api/students/{id}                    # Get student details
```

#### Admin Endpoints (Requires ADMIN role)
```http
# Student Management
GET    /api/students           # List all students
POST   /api/students           # Create student
PUT    /api/students/{id}      # Update student
DELETE /api/students/{id}      # Delete student

# Course Management
POST   /api/courses            # Create course
PUT    /api/courses/{id}       # Update course
DELETE /api/courses/{id}       # Delete course

# Enrollment Management
GET    /api/enrollments        # List all enrollments
```

### Example Requests

**Get authentication token:**
```powershell
# Run the provided script
.\test-keycloak-tokens-fixed.ps1
```

**Create enrollment (as student):**
```bash
curl -X POST http://localhost:8080/api/enrollments \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "courseId": 1
  }'
```

---

## 🔐 Security

### Authentication
The system uses **OAuth2 with JWT tokens** via Keycloak.

**Getting a token:**
```bash
curl -X POST http://localhost:8180/realms/university-realm/protocol/openid-connect/token \
  -d "grant_type=password" \
  -d "client_id=course-registration-api" \
  -d "client_secret=YOUR_CLIENT_SECRET" \
  -d "username=student1" \
  -d "password=password"
```

### Authorization Roles

| Role | Permissions |
|------|-------------|
| **PUBLIC** | View courses |
| **STUDENT** | Enroll in courses, view own enrollments |
| **ADMIN** | Full access to all endpoints |

### Default Users

| Username | Password | Role |
|----------|----------|------|
| student1 | password | STUDENT |
| admin1 | password | ADMIN |

---

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Test Coverage

**Unit Tests** (6 tests)
- Service layer business logic
- Mock dependencies with Mockito
- Error handling and validation

**Integration Tests** (4 tests)
- REST API endpoints
- Spring Security configuration
- Role-based access control

**Kafka Tests** (1 test)
- Event production
- Message serialization
- Embedded Kafka

**Total: 12 tests**

### Test Scripts
Automated testing scripts are provided:
```bash
# Get authentication tokens
.\test-keycloak-tokens-fixed.ps1

# Test all API endpoints
.\test-api-endpoints-fixed.ps1
```

---

## 📁 Project Structure

```
course-registration/
├── src/
│   ├── main/
│   │   ├── java/com/university/courseregistration/
│   │   │   ├── config/          # Configuration classes
│   │   │   │   ├── CorsConfig.java
│   │   │   │   ├── JwtAuthConverter.java
│   │   │   │   ├── KafkaTopicConfig.java
│   │   │   │   ├── OpenApiConfig.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/      # REST endpoints
│   │   │   │   ├── CourseController.java
│   │   │   │   ├── EnrollmentController.java
│   │   │   │   └── StudentController.java
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── CourseDTO.java
│   │   │   │   ├── EnrollmentCreatedEvent.java
│   │   │   │   ├── EnrollmentDTO.java
│   │   │   │   └── StudentDTO.java
│   │   │   ├── entity/          # JPA Entities
│   │   │   │   ├── Course.java
│   │   │   │   ├── Enrollment.java
│   │   │   │   └── Student.java
│   │   │   ├── exception/       # Custom exceptions
│   │   │   │   ├── DuplicateResourceException.java
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── kafka/           # Kafka integration
│   │   │   │   ├── KafkaConsumerService.java
│   │   │   │   └── KafkaProducerService.java
│   │   │   ├── repository/      # Data access layer
│   │   │   │   ├── CourseRepository.java
│   │   │   │   ├── EnrollmentRepository.java
│   │   │   │   └── StudentRepository.java
│   │   │   ├── service/         # Business logic
│   │   │   │   ├── CourseService.java
│   │   │   │   ├── EnrollmentService.java
│   │   │   │   └── StudentService.java
│   │   │   └── CourseRegistrationApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/    # Flyway migrations
│   │           ├── V1__Create_students_table.sql
│   │           ├── V2__Create_courses_table.sql
│   │           ├── V3__Create_enrollments_table.sql
│   │           └── V4__Insert_test_data.sql
│   └── test/                    # Test suites
│       └── java/com/university/courseregistration/
│           ├── controller/
│           │   └── StudentControllerIntegrationTest.java
│           ├── kafka/
│           │   └── KafkaIntegrationTest.java
│           └── service/
│               └── StudentServiceTest.java
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

---

## 🗄 Database Schema

### Students Table
```sql
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    student_id VARCHAR(20) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Courses Table
```sql
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    capacity INTEGER NOT NULL,
    enrolled INTEGER DEFAULT 0,
    instructor VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Enrollments Table
```sql
CREATE TABLE enrollments (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    UNIQUE (student_id, course_id)
);
```

### Relationships
- **One-to-Many**: Student → Enrollments
- **One-to-Many**: Course → Enrollments
- **Many-to-Many**: Students ↔ Courses (through Enrollments)

---

## 📡 Event Flow

### Enrollment Created Event

When a student enrolls in a course:

1. **API receives POST request** to `/api/enrollments`
2. **Service validates** enrollment (capacity, duplicates)
3. **Database saves** enrollment record
4. **Kafka producer publishes** `EnrollmentCreatedEvent`
5. **Kafka consumer receives** event
6. **Consumer processes** (logs notification, could send email)

### Event Structure
```json
{
  "enrollmentId": 1,
  "studentId": 1,
  "courseId": 1,
  "courseName": "Introduction to Computer Science",
  "studentName": "Alice Johnson",
  "enrolledAt": "2025-12-29T12:00:00"
}
```

---

## 🔧 Configuration

### Application Properties
Key configurations in `application.yml`:

```yaml
# Database
spring.datasource.url: jdbc:postgresql://localhost:5432/course_registration_db

# Kafka
spring.kafka.bootstrap-servers: localhost:9092

# Keycloak
spring.security.oauth2.resourceserver.jwt.issuer-uri: 
  http://localhost:8180/realms/university-realm

# Server
server.port: 8080
```

### Environment Variables
Override configurations via environment variables:
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/course_registration_db
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092
```

---

## 🐛 Troubleshooting

### Common Issues

**Port already in use**
```bash
# Check what's using the port
netstat -ano | findstr :8080

# Kill the process or change port in application.yml
```

**Keycloak not accessible**
```bash
# Check container status
docker-compose ps keycloak

# View logs
docker logs course-registration-keycloak
```

**Database connection failed**
```bash
# Ensure PostgreSQL is running
docker-compose ps postgres

# Check credentials in application.yml
```

**401 Unauthorized errors**
```bash
# Get fresh tokens (expire in 5 minutes)
.\test-keycloak-tokens-fixed.ps1
```

---

## 📈 Future Enhancements

- [ ] Frontend UI (React/Vue)
- [ ] Email notifications via Kafka consumer
- [ ] Course prerequisites and scheduling
- [ ] Student grades and transcripts
- [ ] Payment integration
- [ ] Caching with Redis
- [ ] Monitoring with Prometheus/Grafana
- [ ] CI/CD pipeline
- [ ] Kubernetes deployment

---

## 👥 Contributor

- Suleimenov Ayan - solo coded

---

## 📞 Support

For issues and questions:
- Create an issue on GitHub
- Contact: ay_suleimenov@kbtu.kz

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Keycloak for robust identity management
- Apache Kafka for reliable event streaming
- Docker for simplified deployment

---

**Built with ❤️ for university course registration**
