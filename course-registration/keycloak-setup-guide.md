# 🔐 Keycloak Setup Guide

## 1. Start Keycloak

```bash
docker-compose up keycloak postgres
```

Wait for Keycloak to start (about 60 seconds).

---

## 2. Access Keycloak Admin Console

Open: **http://localhost:8180**

**Login:**
- Username: `admin`
- Password: `admin`

---

## 3. Create Realm

1. Click dropdown in top-left (currently says "master")
2. Click **"Create Realm"**
3. Realm name: `university-realm`
4. Click **"Create"**

---

## 4. Create Client

1. Go to **Clients** → **Create client**
2. Fill in:
    - **Client ID:** `course-registration-api`
    - **Client authentication:** ON (toggle)
    - **Authorization:** OFF
3. Click **Next**
4. **Valid redirect URIs:** `http://localhost:8080/*`
5. **Web origins:** `http://localhost:8080`
6. Click **Save**

7. Go to **Credentials** tab
8. Copy **Client Secret** (save it for later)

---

## 5. Create Roles

1. Go to **Realm roles** → **Create role**

**Role 1:**
- Role name: `STUDENT`
- Description: `Student role for course enrollment`
- Click **Save**

**Role 2:**
- Role name: `ADMIN`
- Description: `Administrator role for managing system`
- Click **Save**

---

## 6. Create Users

### User 1: Student

1. Go to **Users** → **Add user**
2. Fill in:
    - Username: `student1`
    - Email: `student1@university.com`
    - First name: `John`
    - Last name: `Doe`
    - Email verified: ON
3. Click **Create**
4. Go to **Credentials** tab:
    - Set password: `password`
    - Temporary: OFF
    - Click **Set password**
5. Go to **Role mapping** tab:
    - Click **Assign role**
    - Select **STUDENT**
    - Click **Assign**

### User 2: Admin

1. Go to **Users** → **Add user**
2. Fill in:
    - Username: `admin1`
    - Email: `admin@university.com`
    - First name: `Admin`
    - Last name: `User`
    - Email verified: ON
3. Click **Create**
4. Go to **Credentials** tab:
    - Set password: `password`
    - Temporary: OFF
    - Click **Set password**
5. Go to **Role mapping** tab:
    - Click **Assign role**
    - Select **ADMIN**
    - Click **Assign**

---

## 7. Test Authentication

### Get Access Token (Student)

```bash
curl -X POST http://localhost:8180/realms/university-realm/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=course-registration-api" \
  -d "client_secret=YOUR_CLIENT_SECRET" \
  -d "username=student1" \
  -d "password=password" \
  -d "grant_type=password"
```

**Response:**
```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI...",
  "expires_in": 300,
  "refresh_expires_in": 1800,
  "token_type": "Bearer"
}
```

Copy the `access_token` value.

---

### Test API with Token

**Public endpoint (no token needed):**
```bash
curl http://localhost:8080/api/courses
```

**Protected endpoint (with student token):**
```bash
curl -X POST http://localhost:8080/api/enrollments \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"studentId": 1, "courseId": 1}'
```

**Admin endpoint (with admin token):**
```bash
# First get admin token:
curl -X POST http://localhost:8180/realms/university-realm/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=course-registration-api" \
  -d "client_secret=YOUR_CLIENT_SECRET" \
  -d "username=admin1" \
  -d "password=password" \
  -d "grant_type=password"

# Then use admin token:
curl -X POST http://localhost:8080/api/students \
  -H "Authorization: Bearer ADMIN_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Jane Doe", "email": "jane@university.com", "studentId": "STU002"}'
```

---

## 8. Test in Swagger UI

1. Open: **http://localhost:8080/swagger-ui.html**
2. Click **"Authorize"** button (top-right)
3. Paste your access token
4. Click **"Authorize"**
5. Now you can test endpoints with authentication

---

## 9. Verify Roles Work

**As STUDENT (should FAIL):**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Authorization: Bearer STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Test", "email": "test@test.com", "studentId": "STU999"}'
```

**Expected:** `403 Forbidden`

**As ADMIN (should SUCCESS):**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Test", "email": "test@test.com", "studentId": "STU999"}'
```

**Expected:** `201 Created`

---

## 🎯 Summary

**Created:**
- ✅ Realm: `university-realm`
- ✅ Client: `course-registration-api`
- ✅ Roles: `STUDENT`, `ADMIN`
- ✅ Users: `student1`, `admin1`

**Endpoints Security:**
- 🔓 Public: `GET /api/courses`
- 🔐 Student: `POST /api/enrollments`
- 🔒 Admin: `POST /api/students`, `POST /api/courses`

---

## 🐛 Troubleshooting

**Problem:** Cannot get token

**Solution:**
- Check client secret is correct
- Check username/password
- Check realm name is exactly `university-realm`

**Problem:** 403 Forbidden even with token

**Solution:**
- Check role is assigned to user in Keycloak
- Check role name matches exactly (`STUDENT`, not `student`)
- Verify token is not expired (expires in 5 minutes)

---

## 📚 Resources

- Keycloak Admin: http://localhost:8180
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html