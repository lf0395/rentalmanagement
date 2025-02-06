package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.RentalProperty;
import de.lfrauenrath.rentalmanagement.repository.RentalPropertyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("rental-properties")
public class RentalPropertyController {
    private final RentalPropertyRepository repository;

    public RentalPropertyController(RentalPropertyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RentalProperty> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<RentalProperty> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public RentalProperty create(@RequestBody RentalProperty property) {
        return repository.save(property);
    }
}
