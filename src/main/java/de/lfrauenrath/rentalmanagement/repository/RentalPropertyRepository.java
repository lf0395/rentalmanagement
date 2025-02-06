package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.RentalProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalPropertyRepository extends JpaRepository<RentalProperty, Long> {
}
