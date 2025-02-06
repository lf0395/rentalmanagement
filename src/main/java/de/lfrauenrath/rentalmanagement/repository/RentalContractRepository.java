package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.RentalContract;
import de.lfrauenrath.rentalmanagement.entity.RentalProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalContractRepository extends JpaRepository<RentalContract, Long> {
}
