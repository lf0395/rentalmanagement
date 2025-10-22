package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.Person;
import de.lfrauenrath.rentalmanagement.repository.PersonRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("persons")
class PersonController {
    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public List<Person> getAll(Authentication authentication) {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Optional<Person> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Person create(@RequestBody Person person) {
        return repository.save(person);
    }
}
