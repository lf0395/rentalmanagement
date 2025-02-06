package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.FinalizedUtilityStatement;
import de.lfrauenrath.rentalmanagement.repository.FinalizedUtilityStatementRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("finalized-statements")
public class FinalizedUtilityStatementController {
    private final FinalizedUtilityStatementRepository repository;

    public FinalizedUtilityStatementController(FinalizedUtilityStatementRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<FinalizedUtilityStatement> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<FinalizedUtilityStatement> getById(@PathVariable Long id) {
        return repository.findById(id);
    }
}
