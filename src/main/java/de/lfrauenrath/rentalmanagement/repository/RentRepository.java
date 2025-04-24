package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.Rent;
import de.lfrauenrath.rentalmanagement.entity.RentalContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {

    @Query("""
    SELECT r FROM Rent r 
    WHERE r.rentalContract = :rentalContract
        AND ((r.endDate >= :startDateAfter AND r.endDate <= :endDateBefore)
        OR (r.startDate <= :startDateAfter AND r.endDate >= :endDateBefore)
        OR (r.startDate <= :endDateBefore AND r.endDate IS NULL))
    """)
    List<Rent> findByRentalContractIdYear(
            @Param("rentalContract") RentalContract rentalContract,
            @Param("startDateAfter") LocalDate startDateAfter,
            @Param("endDateBefore") LocalDate endDateBefore
    );
}
