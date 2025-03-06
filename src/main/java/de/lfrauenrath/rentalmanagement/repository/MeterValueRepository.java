package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.Meter;
import de.lfrauenrath.rentalmanagement.entity.MeterValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MeterValueRepository extends JpaRepository<MeterValue, Long> {

    List<MeterValue> findAllByMeterAndMeasureDateBetweenOrderByMeasureDateAsc(Meter meter, LocalDate measureDateAfter, LocalDate measureDateBefore);
}
