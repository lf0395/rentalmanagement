package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.FinalizedUtilityStatement;
import de.lfrauenrath.rentalmanagement.repository.FinalizedUtilityStatementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
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
