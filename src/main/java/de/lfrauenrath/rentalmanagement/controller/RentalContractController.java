package de.lfrauenrath.rentalmanagement.controller;

import de.lfrauenrath.rentalmanagement.entity.Rent;
import de.lfrauenrath.rentalmanagement.entity.RentalContract;
import de.lfrauenrath.rentalmanagement.repository.RentalContractRepository;
import de.lfrauenrath.rentalmanagement.repository.RentalPropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
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

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id, @RequestBody Map<String, String> body) {
        RentalContract contract = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mietvertrag nicht gefunden"));

        LocalDate endDate = LocalDate.now();
        if (!body.containsKey("endDate")) {
            endDate = LocalDate.parse(body.get("endDate")); // Konvertiert das Datum aus dem JSON-Body
        }
        contract.setEndDate(endDate); // Setzt das Enddatum statt direkten Löschen

        repository.save(contract); // Speichert die Änderung
    }

    @PostMapping
    public RentalContract create(@RequestBody RentalContract contract) {
        RentalContract dbContract;

        if (contract.getId() != null) {
            dbContract = repository.findById(contract.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mietvertrag nicht gefunden"));

            if (dbContract.getRentalProperty().getId() != contract.getRentalProperty().getId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mietvertrag kann nicht auf eine andere Immobilie verschoben werden");
            }

            dbContract.setStartDate(contract.getStartDate());
            dbContract.setEndDate(contract.getEndDate());
            dbContract.setTenants(new ArrayList<>(contract.getTenants()));

            // ✅ ALTE RENTS BEIBEHALTEN & NUR NEUE HINZUFÜGEN
            List<Rent> updatedRents = dbContract.getRents();
            if (updatedRents.size() != contract.getRents().size()) {
                if (updatedRents.isEmpty()) {
                    updatedRents.addAll(contract.getRents());
                } else {
                    Rent endDateRent = updatedRents.get(updatedRents.size()-1);
                    endDateRent.setEndDate(contract.getRents().get(contract.getRents().size()-1).getStartDate());
                    updatedRents.set(updatedRents.size()-1, endDateRent);
                    updatedRents.add(contract.getRents().get(contract.getRents().size()-1));
                }
            }
            dbContract.setRents(updatedRents);

            dbContract.setFinalizedUtilityStatements(new ArrayList<>(contract.getFinalizedUtilityStatements()));

        } else {
            dbContract = new RentalContract();
            dbContract.setStartDate(contract.getStartDate());
            dbContract.setEndDate(contract.getEndDate());
            dbContract.setRentalProperty(propertyRepository.findById(contract.getRentalProperty().getId())
                    .orElseThrow(() -> new RuntimeException("Rental Property not found")));
            dbContract.setTenants(new ArrayList<>(contract.getTenants()));
            dbContract.setRents(new ArrayList<>(contract.getRents()));
            dbContract.setFinalizedUtilityStatements(new ArrayList<>(contract.getFinalizedUtilityStatements()));
        }
        return repository.save(dbContract);
    }
}
