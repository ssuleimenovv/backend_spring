# Keycloak Token Test Script
# Run in PowerShell (VS Code Terminal)

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "KEYCLOAK TOKEN TEST" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

$keycloakUrl = "http://localhost:8180/realms/university-realm/protocol/openid-connect/token"
$clientId = "course-registration-api"
$clientSecret = "XIHicLIG6DswFdUMXigC78i9E2CV2Yzk"

Write-Host "1. Getting token for STUDENT (student1)..." -ForegroundColor Yellow

$studentBody = @{
    client_id     = $clientId
    client_secret = $clientSecret
    username      = "student1"
    password      = "password"
    grant_type    = "password"
}

try {
    $studentResponse = Invoke-RestMethod -Uri $keycloakUrl -Method Post -Body $studentBody -ContentType "application/x-www-form-urlencoded"
    
    Write-Host "SUCCESS! Token received for student1" -ForegroundColor Green
    Write-Host ""
    Write-Host "Access Token (first 50 chars):" -ForegroundColor Cyan
    Write-Host $studentResponse.access_token.Substring(0, 50) -ForegroundColor White
    Write-Host "..."
    Write-Host ""
    Write-Host "Expires in: $($studentResponse.expires_in) seconds" -ForegroundColor Gray
    Write-Host "Token type: $($studentResponse.token_type)" -ForegroundColor Gray
    Write-Host ""
    
    $studentResponse.access_token | Out-File -FilePath "student_token.txt" -Encoding UTF8
    Write-Host "Student token saved to: student_token.txt" -ForegroundColor Green
    Write-Host ""
    
    $env:STUDENT_TOKEN = $studentResponse.access_token
    
}
catch {
    Write-Host "ERROR getting token for student1!" -ForegroundColor Red
    Write-Host "Details: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $reader.BaseStream.Position = 0
        $reader.DiscardBufferedData()
        $responseBody = $reader.ReadToEnd()
        Write-Host "Response: $responseBody" -ForegroundColor Red
    }
    Write-Host ""
}

Write-Host "2. Getting token for ADMIN (admin1)..." -ForegroundColor Yellow

$adminBody = @{
    client_id     = $clientId
    client_secret = $clientSecret
    username      = "admin1"
    password      = "password"
    grant_type    = "password"
}

try {
    $adminResponse = Invoke-RestMethod -Uri $keycloakUrl -Method Post -Body $adminBody -ContentType "application/x-www-form-urlencoded"
    
    Write-Host "SUCCESS! Token received for admin1" -ForegroundColor Green
    Write-Host ""
    Write-Host "Access Token (first 50 chars):" -ForegroundColor Cyan
    Write-Host $adminResponse.access_token.Substring(0, 50) -ForegroundColor White
    Write-Host "..."
    Write-Host ""
    Write-Host "Expires in: $($adminResponse.expires_in) seconds" -ForegroundColor Gray
    Write-Host "Token type: $($adminResponse.token_type)" -ForegroundColor Gray
    Write-Host ""
    
    $adminResponse.access_token | Out-File -FilePath "admin_token.txt" -Encoding UTF8
    Write-Host "Admin token saved to: admin_token.txt" -ForegroundColor Green
    Write-Host ""
    
    $env:ADMIN_TOKEN = $adminResponse.access_token
    
}
catch {
    Write-Host "ERROR getting token for admin1!" -ForegroundColor Red
    Write-Host "Details: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $reader.BaseStream.Position = 0
        $reader.DiscardBufferedData()
        $responseBody = $reader.ReadToEnd()
        Write-Host "Response: $responseBody" -ForegroundColor Red
    }
    Write-Host ""
}

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "TESTING COMPLETED" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Tokens saved to files:" -ForegroundColor Yellow
Write-Host "   - student_token.txt" -ForegroundColor White
Write-Host "   - admin_token.txt" -ForegroundColor White
Write-Host ""
Write-Host "To use tokens in next requests:" -ForegroundColor Yellow
Write-Host "   `$env:STUDENT_TOKEN - for student" -ForegroundColor White
Write-Host "   `$env:ADMIN_TOKEN - for admin" -ForegroundColor White
Write-Host ""
