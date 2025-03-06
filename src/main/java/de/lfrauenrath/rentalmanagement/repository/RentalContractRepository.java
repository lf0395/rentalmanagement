package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.Apartment;
import de.lfrauenrath.rentalmanagement.entity.House;
import de.lfrauenrath.rentalmanagement.entity.RentalContract;
import de.lfrauenrath.rentalmanagement.entity.RentalProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RentalContractRepository extends JpaRepository<RentalContract, Long> {

    @Query("""
    SELECT rc FROM RentalContract rc 
    WHERE rc.rentalProperty = :rentalProperty 
    AND rc.startDate <= :startDateAfter 
    AND (rc.endDate > :endDateBefore OR rc.endDate IS NULL)
    """)
    List<RentalContract> findValidContracts(
            @Param("rentalProperty") RentalProperty rentalProperty,
            @Param("startDateAfter") LocalDate startDateAfter,
            @Param("endDateBefore") LocalDate endDateBefore);
}
