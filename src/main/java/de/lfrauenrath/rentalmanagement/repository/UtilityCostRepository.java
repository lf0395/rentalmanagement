package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.RentalProperty;
import de.lfrauenrath.rentalmanagement.entity.UtilityCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilityCostRepository extends JpaRepository<UtilityCost, Long> {
}
