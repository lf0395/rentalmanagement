package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.RentalContract;
import de.lfrauenrath.rentalmanagement.repository.RentalContractRepository;
import de.lfrauenrath.rentalmanagement.repository.RentalPropertyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("rental-contracts")
public class RentalContractController {
    private final RentalContractRepository repository;
    private final RentalPropertyRepository propertyRepository;

    public RentalContractController(RentalContractRepository repository, RentalPropertyRepository propertyRepository) {
        this.repository = repository;
        this.propertyRepository = propertyRepository;
    }

    @GetMapping
    public List<RentalContract> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<RentalContract> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public RentalContract create(@RequestBody RentalContract contract) {
        contract.setRentalProperty(propertyRepository.findById(contract.getRentalProperty().getId())
                .orElseThrow(() -> new RuntimeException("Rental Property not found")));
        return repository.save(contract);
    }
}
