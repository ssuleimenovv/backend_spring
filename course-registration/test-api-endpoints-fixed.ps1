# API Endpoint Testing Script
# Run in PowerShell (VS Code Terminal)

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "API ENDPOINT TESTING" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

$apiBaseUrl = "http://localhost:8080/api"

if (Test-Path "student_token.txt") {
    $studentToken = Get-Content "student_token.txt" -Raw
    $studentToken = $studentToken.Trim()
    Write-Host "Student token loaded from file" -ForegroundColor Green
}
else {
    Write-Host "File student_token.txt not found. Run test-keycloak-tokens-fixed.ps1 first" -ForegroundColor Yellow
    $studentToken = $null
}

if (Test-Path "admin_token.txt") {
    $adminToken = Get-Content "admin_token.txt" -Raw
    $adminToken = $adminToken.Trim()
    Write-Host "Admin token loaded from file" -ForegroundColor Green
}
else {
    Write-Host "File admin_token.txt not found. Run test-keycloak-tokens-fixed.ps1 first" -ForegroundColor Yellow
    $adminToken = $null
}

Write-Host ""

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "1. PUBLIC: GET /api/courses (no token)" -ForegroundColor Yellow
Write-Host "================================================" -ForegroundColor Cyan

try {
    $coursesResponse = Invoke-RestMethod -Uri "$apiBaseUrl/courses" -Method Get
    Write-Host "SUCCESS! Courses found: $($coursesResponse.Count)" -ForegroundColor Green
    $coursesResponse | Format-Table -AutoSize
}
catch {
    Write-Host "ERROR!" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
}
Write-Host ""

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "2. STUDENT: POST /api/enrollments (with student token)" -ForegroundColor Yellow
Write-Host "================================================" -ForegroundColor Cyan

if ($studentToken) {
    $enrollmentData = @{
        studentId = 1
        courseId  = 1
    } | ConvertTo-Json

    $headers = @{
        "Authorization" = "Bearer $studentToken"
        "Content-Type"  = "application/json"
    }

    try {
        $enrollmentResponse = Invoke-RestMethod -Uri "$apiBaseUrl/enrollments" -Method Post -Headers $headers -Body $enrollmentData
        Write-Host "SUCCESS! Enrollment created:" -ForegroundColor Green
        $enrollmentResponse | Format-List
    }
    catch {
        Write-Host "ERROR!" -ForegroundColor Red
        Write-Host $_.Exception.Message -ForegroundColor Red
        if ($_.Exception.Response.StatusCode -eq 400) {
            Write-Host "NOTE: Student may already be enrolled in this course" -ForegroundColor Yellow
        }
    }
}
else {
    Write-Host "Student token not found, test skipped" -ForegroundColor Yellow
}
Write-Host ""

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "3. ADMIN: POST /api/students (with admin token)" -ForegroundColor Yellow
Write-Host "================================================" -ForegroundColor Cyan

if ($adminToken) {
    $randomNum = Get-Random -Minimum 100 -Maximum 999
    $studentData = @{
        name      = "Test Student $randomNum"
        email     = "test$randomNum@university.com"
        studentId = "STU$randomNum"
    } | ConvertTo-Json

    $headers = @{
        "Authorization" = "Bearer $adminToken"
        "Content-Type"  = "application/json"
    }

    try {
        $newStudentResponse = Invoke-RestMethod -Uri "$apiBaseUrl/students" -Method Post -Headers $headers -Body $studentData
        Write-Host "SUCCESS! Student created:" -ForegroundColor Green
        $newStudentResponse | Format-List
    }
    catch {
        Write-Host "ERROR!" -ForegroundColor Red
        Write-Host $_.Exception.Message -ForegroundColor Red
    }
}
else {
    Write-Host "Admin token not found, test skipped" -ForegroundColor Yellow
}
Write-Host ""

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "4. SECURITY TEST: STUDENT tries to create student" -ForegroundColor Yellow
Write-Host "    Expected: 403 Forbidden" -ForegroundColor Gray
Write-Host "================================================" -ForegroundColor Cyan

if ($studentToken) {
    $studentData = @{
        name      = "Forbidden Student"
        email     = "forbidden@test.com"
        studentId = "STU000"
    } | ConvertTo-Json

    $headers = @{
        "Authorization" = "Bearer $studentToken"
        "Content-Type"  = "application/json"
    }

    try {
        $forbiddenResponse = Invoke-RestMethod -Uri "$apiBaseUrl/students" -Method Post -Headers $headers -Body $studentData
        Write-Host "PROBLEM! Student was able to create student (should not be allowed)" -ForegroundColor Red
    }
    catch {
        if ($_.Exception.Response.StatusCode.value__ -eq 403) {
            Write-Host "EXCELLENT! Got 403 Forbidden (as expected)" -ForegroundColor Green
            Write-Host "   Security is working correctly!" -ForegroundColor Green
        }
        else {
            Write-Host "Got different error: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Yellow
            Write-Host $_.Exception.Message -ForegroundColor Yellow
        }
    }
}
else {
    Write-Host "Student token not found, test skipped" -ForegroundColor Yellow
}
Write-Host ""

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "5. STUDENT: GET /api/enrollments/student/1 (with student token)" -ForegroundColor Yellow
Write-Host "================================================" -ForegroundColor Cyan

if ($studentToken) {
    $headers = @{
        "Authorization" = "Bearer $studentToken"
    }

    try {
        $myEnrollmentsResponse = Invoke-RestMethod -Uri "$apiBaseUrl/enrollments/student/1" -Method Get -Headers $headers
        Write-Host "SUCCESS! Found enrollments: $($myEnrollmentsResponse.Count)" -ForegroundColor Green
        $myEnrollmentsResponse | Format-Table -AutoSize
    }
    catch {
        Write-Host "ERROR!" -ForegroundColor Red
        Write-Host $_.Exception.Message -ForegroundColor Red
    }
}
else {
    Write-Host "Student token not found, test skipped" -ForegroundColor Yellow
}
Write-Host ""

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "API TESTING COMPLETED" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""
