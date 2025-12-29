package com.smattme.spring_boot_flyway.controller;

import com.smattme.spring_boot_flyway.model.Person;
import com.smattme.spring_boot_flyway.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Person> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person Not Found"));
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return repository.save(person);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable Long id, @RequestBody Person updated) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person Not Found"));
        person.setName(updated.getName());
        person.setAge(updated.getAge());
        person.setEmail(updated.getEmail());
        return repository.save(person);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "Person deleted successfully";
    }

}
