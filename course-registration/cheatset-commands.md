# 1. Запишем студента ID=3 на курс ID=3
```
$body = @{
studentId = 3
courseId  = 3
} | ConvertTo-Json

Invoke-WebRequest -Uri http://localhost:8080/api/enrollments `
    -Method POST `
-Headers @{Authorization="Bearer $studentToken"; "Content-Type"="application/json"} `
-Body $body
```
# 2. Local Run
```
mvn spring-boot:run
```
# 3. Checking PS scripts(tokens)
```
.\test-keycloak-tokens-fixed.ps1
.\test-api-endpoints-fixed.ps1
```
# 4. Running APP locally + DOCKER for other containers
```
docker-compose up -d postgres kafka zookeeper keycloak
```
# 5. Notifications URL
```
http://localhost:8081/api/notifications
```
# 6. Keycloak - University-Realm
```
http://localhost:8180/admin/master/console/#/university-realm/
```
# 7. Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```