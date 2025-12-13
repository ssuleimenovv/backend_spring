package com.smattme.spring_boot_flyway.repository;
import com.smattme.spring_boot_flyway.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> { }