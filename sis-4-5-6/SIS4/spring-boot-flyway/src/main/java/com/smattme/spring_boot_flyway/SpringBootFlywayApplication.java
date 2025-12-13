package com.smattme.spring_boot_flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootFlywayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFlywayApplication.class, args);
	}

}

// docker run --name postgres-spring-boot-flyway -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=1000tenge -e POSTGRES_DB=spring-boot-flyway -p 5432:5432 -d postgres:17