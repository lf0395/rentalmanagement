package de.lfrauenrath.rentalmanagement.repository;

import de.lfrauenrath.rentalmanagement.entity.FinalizedUtilityStatement;
import de.lfrauenrath.rentalmanagement.entity.UtilityStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinalizedUtilityStatementRepository extends JpaRepository<FinalizedUtilityStatement, Long> {

      List<FinalizedUtilityStatement> findByUtilityStatement(UtilityStatement utilityStatement);
}
