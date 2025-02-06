package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.Person;
import de.lfrauenrath.rentalmanagement.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("persons")
class PersonController {
    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Person> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return repository.save(person);
    }
}
