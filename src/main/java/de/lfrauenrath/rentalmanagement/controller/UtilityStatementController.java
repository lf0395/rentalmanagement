package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.UtilityStatement;
import de.lfrauenrath.rentalmanagement.repository.RentalPropertyRepository;
import de.lfrauenrath.rentalmanagement.repository.UtilityStatementRepository;
import de.lfrauenrath.rentalmanagement.service.UtilityStatementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("utility-statements")
public class UtilityStatementController {
    private final UtilityStatementRepository repository;
    private final UtilityStatementService service;
    private final RentalPropertyRepository rentalPropertyRepository;

    public UtilityStatementController(UtilityStatementRepository repository, UtilityStatementService service,
                                      RentalPropertyRepository rentalPropertyRepository) {
        this.repository = repository;
        this.service = service;
        this.rentalPropertyRepository = rentalPropertyRepository;
    }

    @GetMapping
    public List<UtilityStatement> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<UtilityStatement> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public UtilityStatement create(@RequestBody UtilityStatement statement) {
        statement.setRentalProperty(rentalPropertyRepository.findById(statement.getRentalProperty().getId())
                .orElseThrow(() -> new RuntimeException("Rental Property not found")));
        return repository.save(statement);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/{id}/finalize")
    public void finalizeStatement(@PathVariable Long id) {
        service.finalizeStatement(id);
    }
}
