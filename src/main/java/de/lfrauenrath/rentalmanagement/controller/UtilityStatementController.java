package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.UtilityStatement;
import de.lfrauenrath.rentalmanagement.repository.UtilityStatementRepository;
import de.lfrauenrath.rentalmanagement.service.UtilityStatementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("utility-statements")
public class UtilityStatementController {
    private final UtilityStatementRepository repository;
    private final UtilityStatementService service;

    public UtilityStatementController(UtilityStatementRepository repository, UtilityStatementService service) {
        this.repository = repository;
        this.service = service;
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
        return repository.save(statement);
    }

    @PostMapping("/{id}/finalize")
    public void finalizeStatement(@PathVariable Long id) {
        service.finalizeStatement(id);
    }
}
