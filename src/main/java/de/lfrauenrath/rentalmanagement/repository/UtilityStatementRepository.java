package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.UtilityStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilityStatementRepository extends JpaRepository<UtilityStatement, Long> {
}
