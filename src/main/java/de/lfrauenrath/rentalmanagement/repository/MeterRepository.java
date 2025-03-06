package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.Meter;
import de.lfrauenrath.rentalmanagement.entity.MeterType;
import de.lfrauenrath.rentalmanagement.entity.RentalProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeterRepository extends JpaRepository<Meter, Long> {

    List<Meter> findByRentalPropertyAndType(RentalProperty rentalProperty, MeterType type);
}
