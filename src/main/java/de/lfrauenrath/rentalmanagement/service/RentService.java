package de.lfrauenrath.rentalmanagement.service;

import de.lfrauenrath.rentalmanagement.entity.Rent;
import de.lfrauenrath.rentalmanagement.entity.RentalContract;
import de.lfrauenrath.rentalmanagement.repository.RentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class RentService {

    private final RentRepository rentRepository;

    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public double calculateAdvancePayment(RentalContract rentalContract, LocalDate startDate, LocalDate endDate) {
        List<Rent> rents = rentRepository.findByRentalContractIdYear(rentalContract, startDate, endDate);
        return rents.stream()
                .mapToDouble(rent -> {
                    LocalDate tmpStart = rent.getStartDate();
                    LocalDate tmpEnd = rent.getEndDate();
                    if (tmpStart.isBefore(startDate)) {
                        tmpStart = startDate;
                    }
                    if (tmpEnd == null || tmpEnd.isAfter(endDate)) {
                        tmpEnd = endDate;
                    }
                    double factor = ChronoUnit.MONTHS.between(tmpStart, tmpEnd) + 1;
                    return rent.getAdditionalCosts() * factor;
                } )
                .sum();
    }
}
