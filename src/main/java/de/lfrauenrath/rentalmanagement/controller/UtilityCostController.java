package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.UtilityCost;
import de.lfrauenrath.rentalmanagement.repository.UtilityCostRepository;
import de.lfrauenrath.rentalmanagement.repository.UtilityStatementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("utility-costs")
class UtilityCostController {
    private final UtilityCostRepository repository;
    private final UtilityStatementRepository statementRepository;

    public UtilityCostController(UtilityCostRepository repository, UtilityStatementRepository utilityStatementRepository) {
        this.repository = repository;
        this.statementRepository = utilityStatementRepository;
    }

    @GetMapping
    public List<UtilityCost> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<UtilityCost> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public UtilityCost create(@RequestBody UtilityCost cost) {
        cost.setUtilityStatement(statementRepository.findById(cost.getUtilityStatement().getId())
                .orElseThrow(() -> new RuntimeException("Utility Statement not found")));
        return repository.save(cost);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public UtilityCost update(@PathVariable Long id, @RequestBody UtilityCost cost) {
        UtilityCost dbCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utility Cost not found"));
        dbCost.setPrice(cost.getPrice());
        dbCost.setName(cost.getName());
        dbCost.setBillable(cost.isBillable());
        dbCost.setBillingType(cost.getBillingType());
        dbCost.setAttachments(cost.getAttachments());
        return repository.save(dbCost);
    }
}
