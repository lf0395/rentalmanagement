package de.lfrauenrath.rentalmanagement.service;

import de.lfrauenrath.rentalmanagement.entity.Apartment;
import de.lfrauenrath.rentalmanagement.entity.House;
import de.lfrauenrath.rentalmanagement.entity.RentalProperty;
import de.lfrauenrath.rentalmanagement.repository.RentalContractRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
public class RentalPropertyService {

    private final RentalContractRepository rentalContractRepository;

    public RentalPropertyService(RentalContractRepository rentalContractRepository) {
        this.rentalContractRepository = rentalContractRepository;
    }

    public double countRentedPersonInHouseWithinTimeframe(House rentedHouse, LocalDate startDate, LocalDate endDate) {
        double amountOfPersons = 0.0;
        for (Apartment apartment : rentedHouse.getApartments()) {
            amountOfPersons += rentalContractRepository.findValidContracts(apartment, startDate, endDate)
                    .stream()
                    .mapToDouble(contract -> contract.getTenants().size())
                    .sum();
        }
        return amountOfPersons;
    }

    public double countRentedPersonInRentalPropertyWithinTimeframe(RentalProperty rentalProperty, LocalDate startDate, LocalDate endDate) {
        if (rentalProperty instanceof House) {
            return rentalContractRepository.findValidContracts((House) rentalProperty, startDate, endDate)
                    .stream()
                    .mapToDouble(contract -> contract.getTenants().size())
                    .sum();
        } else {
            return rentalContractRepository.findValidContracts((Apartment) rentalProperty, startDate, endDate)
                    .stream()
                    .mapToDouble(contract -> contract.getTenants().size())
                    .sum();
        }
    }
}
