package de.lfrauenrath.rentalmanagement.service;

import de.lfrauenrath.rentalmanagement.entity.*;
import de.lfrauenrath.rentalmanagement.repository.FinalizedUtilityStatementRepository;
import de.lfrauenrath.rentalmanagement.repository.UtilityStatementRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.actuate.startup.StartupEndpoint;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Transactional
public class UtilityStatementService {
    private final UtilityStatementRepository utilityStatementRepository;
    private final FinalizedUtilityStatementRepository finalizedUtilityStatementRepository;

    public UtilityStatementService(UtilityStatementRepository utilityStatementRepository,
                                   FinalizedUtilityStatementRepository finalizedUtilityStatementRepository) {
        this.utilityStatementRepository = utilityStatementRepository;
        this.finalizedUtilityStatementRepository = finalizedUtilityStatementRepository;
    }

    public void finalizeStatement(Long statementId) {
        UtilityStatement statement = utilityStatementRepository.findById(statementId)
                .orElseThrow(() -> new RuntimeException("Utility statement not found"));

        RentalProperty rentalProperty = statement.getRentalProperty();

        if (rentalProperty instanceof House) {
            if (!((House) statement.getRentalProperty()).getApartments().isEmpty()) {
                handleMultipleRentedApartmentsInHouse(statement);
            } else {
                handleRentedProperty(statement);
            }
        } else {
            handleRentedProperty(statement);
        }
    }

    private void handleRentedProperty(UtilityStatement statement) {
        FinalizedUtilityStatement finalizedStatement = new FinalizedUtilityStatement();
        finalizedStatement.setUtilityStatement(statement);
        finalizedStatement.setRentalProperty(statement.getRentalProperty());
        finalizedStatement.setIndividualCost(statement.getTotalCost());
        finalizedStatement.setBreakdown("Total cost for the year " + statement.getYear());

        for (UtilityCost cost: statement.getUtilityCosts()) {
            FinalizedUtilityCost finalizedCost = new FinalizedUtilityCost();
            finalizedCost.setTotalCost(cost.getPrice());
            finalizedCost.setFinalizedAttachments(Collections.emptyList());
            finalizedCost.setBillingType(cost.getBillingType());
            finalizedCost.setFinalizedStatement(finalizedStatement);
            finalizedCost.setIndividualShare(calculateIndividualCosts(cost));
        }

        finalizedUtilityStatementRepository.save(finalizedStatement);
    }

    private void handleMultipleRentedApartmentsInHouse(UtilityStatement statement) {

    }

    private double calculateIndividualCosts(UtilityCost cost) {
        switch (cost.getBillingType()) {
            case PERSONS -> {
                return cost.getPrice() ;
            }
            case SQUARE_METERS -> {
                return cost.getPrice() ;
            }
            case FLAT_RATE -> {
                return cost.getPrice();
            }
            case CONSUMPTION -> {
                return cost.getPrice();
            }
        }
        return 0.0;
    }
}
