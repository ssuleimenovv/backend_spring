package com.smattme.spring_boot_flyway;

import com.smattme.spring_boot_flyway.seeders.FlywayDatabaseSeeder;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootFlywayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFlywayApplication.class, args);
    }

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy(@Value("${spring.profiles.active}") String activeProfile) {
        return (flywayOld) -> {
            Flyway flyway = Flyway.configure()
                    .configuration(flywayOld.getConfiguration())
                    .callbacks(new FlywayDatabaseSeeder())
                    .load();

            if (!"local".equalsIgnoreCase(activeProfile)) {
                flyway.migrate();
            }
        };
    }
}
