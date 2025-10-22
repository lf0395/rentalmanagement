package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.UtilityStatement;
import de.lfrauenrath.rentalmanagement.repository.RentalPropertyRepository;
import de.lfrauenrath.rentalmanagement.repository.UtilityStatementRepository;
import de.lfrauenrath.rentalmanagement.service.UtilityStatementService;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public List<UtilityStatement> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public Optional<UtilityStatement> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user','admin')")
    public UtilityStatement create(@RequestBody UtilityStatement statement) {
        UtilityStatement utilityStatement = new UtilityStatement();
        utilityStatement.setRentalProperty(rentalPropertyRepository.findById(statement.getRentalProperty().getId())
                .orElseThrow(() -> new RuntimeException("Rental Property not found")));
        return repository.save(statement);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public void deleteById(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/{id}/finalize")
    @PreAuthorize("hasAnyAuthority('admin')")
    public void finalizeStatement(@PathVariable Long id) {
        service.finalizeStatement(id);
    }
}
