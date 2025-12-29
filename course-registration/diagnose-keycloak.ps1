# Keycloak Diagnostics Script
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "KEYCLOAK DIAGNOSTICS" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "1. Checking if Keycloak is accessible..." -ForegroundColor Yellow
try {
    $keycloakHealth = Invoke-WebRequest -Uri "http://localhost:8180" -Method Get -TimeoutSec 5
    Write-Host "SUCCESS: Keycloak is accessible on port 8180" -ForegroundColor Green
}
catch {
    Write-Host "ERROR: Cannot reach Keycloak on http://localhost:8180" -ForegroundColor Red
    Write-Host "Make sure Keycloak Docker container is running!" -ForegroundColor Yellow
    Write-Host ""
}

Write-Host ""
Write-Host "2. Checking Keycloak realm configuration endpoint..." -ForegroundColor Yellow
try {
    $realmConfig = Invoke-RestMethod -Uri "http://localhost:8180/realms/university-realm/.well-known/openid-configuration" -Method Get
    Write-Host "SUCCESS: Realm 'university-realm' is configured" -ForegroundColor Green
    Write-Host "Issuer: $($realmConfig.issuer)" -ForegroundColor Cyan
    Write-Host "Token endpoint: $($realmConfig.token_endpoint)" -ForegroundColor Cyan
    Write-Host "JWK Set URI: $($realmConfig.jwks_uri)" -ForegroundColor Cyan
}
catch {
    Write-Host "ERROR: Cannot access realm configuration" -ForegroundColor Red
    Write-Host "Check if realm 'university-realm' exists in Keycloak" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "3. Checking if Spring Boot app is running..." -ForegroundColor Yellow
try {
    $appHealth = Invoke-WebRequest -Uri "http://localhost:8080/api/courses" -Method Get -TimeoutSec 5
    Write-Host "SUCCESS: Spring Boot app is accessible on port 8080" -ForegroundColor Green
}
catch {
    Write-Host "ERROR: Cannot reach Spring Boot app on http://localhost:8080" -ForegroundColor Red
    Write-Host "Make sure the application is running (mvn spring-boot:run)" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "4. Checking Docker containers status..." -ForegroundColor Yellow
Write-Host ""
docker ps --filter "name=course-registration" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

Write-Host ""
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "DIAGNOSTICS COMPLETED" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "NEXT STEPS:" -ForegroundColor Yellow
Write-Host ""
Write-Host "If Keycloak is not accessible:" -ForegroundColor Cyan
Write-Host "  docker-compose up -d keycloak postgres" -ForegroundColor White
Write-Host ""
Write-Host "If Spring Boot app is not running:" -ForegroundColor Cyan
Write-Host "  mvn spring-boot:run" -ForegroundColor White
Write-Host ""
Write-Host "If both are running but still 401 error:" -ForegroundColor Cyan
Write-Host "  Check application.yml configuration" -ForegroundColor White
Write-Host ""
