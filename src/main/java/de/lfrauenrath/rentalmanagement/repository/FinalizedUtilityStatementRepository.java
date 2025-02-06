package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.FinalizedUtilityStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinalizedUtilityStatementRepository extends JpaRepository<FinalizedUtilityStatement, Long> {
}
