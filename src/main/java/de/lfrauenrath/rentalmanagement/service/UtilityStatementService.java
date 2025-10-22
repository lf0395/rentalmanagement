package de.lfrauenrath.rentalmanagement.service;

import de.lfrauenrath.rentalmanagement.entity.*;
import de.lfrauenrath.rentalmanagement.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UtilityStatementService {
    private final UtilityStatementRepository utilityStatementRepository;
    private final FinalizedUtilityStatementRepository finalizedUtilityStatementRepository;
    private final RentService rentService;
    private final RentalContractRepository rentalContractRepository;
    private final MeterService meterService;
    private final PdfService pdfService;

    public UtilityStatementService(UtilityStatementRepository utilityStatementRepository,
                                   FinalizedUtilityStatementRepository finalizedUtilityStatementRepository,
                                   RentService rentService,
                                   RentalContractRepository rentalContractRepository,
                                   MeterService meterService,
                                   PdfService pdfService) {
        this.utilityStatementRepository = utilityStatementRepository;
        this.finalizedUtilityStatementRepository = finalizedUtilityStatementRepository;
        this.rentService = rentService;
        this.rentalContractRepository = rentalContractRepository;
        this.meterService = meterService;
        this.pdfService = pdfService;
    }

    public void finalizeStatement(Long statementId) {
        UtilityStatement statement = utilityStatementRepository.findById(statementId)
                .orElseThrow(() -> new RuntimeException("Utility statement not found"));

        if (!finalizedUtilityStatementRepository.findByUtilityStatement(statement).isEmpty()) {
            throw new RuntimeException(String.format("Die Nebenkostenaufstellung für %s im Jahr %d wurde bereits abgerechnet.",
                    statement.getRentalProperty().getFullAddress(),
                    statement.getYear()));
        }
        RentalProperty rentalProperty = statement.getRentalProperty();

        if (rentalProperty instanceof House) {
            if (!((House) rentalProperty).getApartments().isEmpty()) {
                handleMultipleRentedApartmentsInHouse(statement, (House) rentalProperty);
            } else {
                handleSingleRentedProperty(statement, rentalProperty);
            }
        } else {
            handleSingleRentedProperty(statement, rentalProperty);
        }
    }

    private void handleSingleRentedProperty(UtilityStatement statement, RentalProperty rentalProperty) {
        double overallSquareMeters = rentalProperty.getLivingArea();
        List<RentalContract> rentalContracts = rentalContractRepository.findValidContracts(rentalProperty, LocalDate.of(statement.getYear(), 1, 1), LocalDate.of(statement.getYear(), 12, 31));
        LocalDate firstDayOfYear = LocalDate.of(statement.getYear(), 1, 1);
        LocalDate lastDayOfYear = LocalDate.of(statement.getYear(), 12, 31);
        double totalPersonMonths = rentalContracts.stream()
                .mapToDouble(contract -> calculateTotalPersonMonths(contract, firstDayOfYear, lastDayOfYear))
                .sum();
        for (RentalContract contract : rentalContracts) {
            FinalizedUtilityStatement finalizedUtilityStatement = new FinalizedUtilityStatement();
            finalizedUtilityStatement.setUtilityStatement(statement);
            finalizedUtilityStatement.setRentalContract(contract);
            finalizedUtilityStatement.setBreakdown("Nebenkostenaufstellung " + statement.getYear());
            finalizedUtilityStatement.setFinalizedUtilityCosts(new ArrayList<>());
            long daysInYear = 365;
            if (LocalDate.of(statement.getYear(), 1, 1).isLeapYear()) {
                daysInYear = 366;
            }
            ;
            LocalDate tempStartDate = contract.getStartDate();
            LocalDate tempEndDate = contract.getEndDate();
            if (tempStartDate.isBefore(firstDayOfYear) ||
                    tempStartDate.isEqual(firstDayOfYear)) {
                tempStartDate = firstDayOfYear;
            }
            if (tempEndDate == null ||
                    tempEndDate.isAfter(lastDayOfYear) ||
                    tempEndDate.isEqual(lastDayOfYear)) {
                tempEndDate = lastDayOfYear;
            }
            long days = ChronoUnit.DAYS.between(tempStartDate, tempEndDate) + 1;
            for (UtilityCost cost : statement.getUtilityCosts()) {
                FinalizedUtilityCost finalizedUtilityCost = new FinalizedUtilityCost();
                finalizedUtilityStatement.getFinalizedUtilityCosts().add(finalizedUtilityCost);
                finalizedUtilityCost.setName(cost.getName());
                finalizedUtilityCost.setTotalCost(cost.getPrice());
                finalizedUtilityCost.setFinalizedStatement(finalizedUtilityStatement);
                finalizedUtilityCost.setBillingType(cost.getBillingType());
                for (Attachment attachment : cost.getAttachments()) {
                    FinalizedAttachment finalizedAttachment = new FinalizedAttachment();
                    finalizedAttachment.setData(attachment.getData());
                    finalizedAttachment.setFinalizedUtilityCost(finalizedUtilityCost);
                    finalizedAttachment.setFileName(attachment.getFileName());
                    finalizedAttachment.setFileType(attachment.getFileType());
                }
                switch (cost.getBillingType()) {
                    case SQUARE_METERS:
                        finalizedUtilityCost.setIndividualShare(cost.getPrice() / overallSquareMeters * contract.getRentalProperty().getLivingArea() / daysInYear * days);
                        break;
                    case PERSONS:
                        finalizedUtilityCost.setIndividualShare(cost.getPrice() / totalPersonMonths * calculateTotalPersonMonths(contract, tempStartDate, tempEndDate));
                        break;
                    case CONSUMPTION:
                        double consumption = meterService.calculateConsumption(contract.getRentalProperty(),
                                tempStartDate, tempEndDate, cost.getMeterType());
                        finalizedUtilityCost.setIndividualShare(cost.getPrice() / cost.getConsumption() * consumption);
                        break;
                    case FLAT_RATE:
                        finalizedUtilityCost.setIndividualShare(cost.getPrice() / daysInYear * days);
                        break;
                }
            }
            finalizedUtilityStatement.setIndividualCost(finalizedUtilityStatement.getFinalizedUtilityCosts().stream().mapToDouble(FinalizedUtilityCost::getIndividualShare).sum());
            finalizedUtilityStatementRepository.save(finalizedUtilityStatement);
        }
        statement.setFinalized(true);
        utilityStatementRepository.save(statement);
    }

    private void handleMultipleRentedApartmentsInHouse(UtilityStatement statement, House house) {
        List<Apartment> apartments = house.getApartments();
        double overallSquareMeters = apartments.stream().mapToDouble(Apartment::getLivingArea).sum();
        List<RentalContract> rentalContracts = apartments.stream()
                .map(apartment -> rentalContractRepository.findValidContracts(apartment, LocalDate.of(statement.getYear(), 1, 1), LocalDate.of(statement.getYear(), 12, 31)))
                .flatMap(List::stream)
                .toList();
        LocalDate firstDayOfYear = LocalDate.of(statement.getYear(), 1, 1);
        LocalDate lastDayOfYear = LocalDate.of(statement.getYear(), 12, 31);
        double totalPersonMonths = rentalContracts.stream()
                .mapToDouble(contract -> calculateTotalPersonMonths(contract, firstDayOfYear, lastDayOfYear))
                .sum();
        for (RentalContract contract : rentalContracts) {
            FinalizedUtilityStatement finalizedUtilityStatement = new FinalizedUtilityStatement();
            finalizedUtilityStatement.setUtilityStatement(statement);
            finalizedUtilityStatement.setRentalContract(contract);
            finalizedUtilityStatement.setBreakdown("Nebenkostenaufstellung " + statement.getYear());
            finalizedUtilityStatement.setFinalizedUtilityCosts(new ArrayList<>());
            long daysInYear = 365;
            if (LocalDate.of(statement.getYear(), 1, 1).isLeapYear()) {
                daysInYear = 366;
            }
            ;
            LocalDate tempStartDate = contract.getStartDate();
            LocalDate tempEndDate = contract.getEndDate();
            if (tempStartDate.isBefore(firstDayOfYear) ||
                    tempStartDate.isEqual(firstDayOfYear)) {
                tempStartDate = firstDayOfYear;
            }
            if (tempEndDate == null ||
                    tempEndDate.isAfter(lastDayOfYear) ||
                    tempEndDate.isEqual(lastDayOfYear)) {
                tempEndDate = lastDayOfYear;
            }
            long days = ChronoUnit.DAYS.between(tempStartDate, tempEndDate) + 1;
            for (UtilityCost cost : statement.getUtilityCosts()) {
                FinalizedUtilityCost finalizedUtilityCost = new FinalizedUtilityCost();
                finalizedUtilityStatement.getFinalizedUtilityCosts().add(finalizedUtilityCost);
                finalizedUtilityCost.setTotalCost(cost.getPrice());
                finalizedUtilityCost.setName(cost.getName());
                finalizedUtilityCost.setFinalizedStatement(finalizedUtilityStatement);
                finalizedUtilityCost.setBillingType(cost.getBillingType());
                for (Attachment attachment : cost.getAttachments()) {
                    FinalizedAttachment finalizedAttachment = new FinalizedAttachment();
                    finalizedAttachment.setData(attachment.getData());
                    finalizedAttachment.setFinalizedUtilityCost(finalizedUtilityCost);
                    finalizedAttachment.setFileName(attachment.getFileName());
                    finalizedAttachment.setFileType(attachment.getFileType());
                }
                switch (cost.getBillingType()) {
                    case SQUARE_METERS:
                        finalizedUtilityCost.setIndividualShare(cost.getPrice() / overallSquareMeters * contract.getRentalProperty().getLivingArea() / daysInYear * days);
                        break;
                    case PERSONS:
                        finalizedUtilityCost.setIndividualShare(cost.getPrice() / totalPersonMonths * calculateTotalPersonMonths(contract, tempStartDate, tempEndDate));
                        break;
                    case CONSUMPTION:
                        double consumption = meterService.calculateConsumption(contract.getRentalProperty(),
                                tempStartDate, tempEndDate, cost.getMeterType());
                        finalizedUtilityCost.setIndividualShare(cost.getPrice() / cost.getConsumption() * consumption);
                        break;
                    case FLAT_RATE:
                        finalizedUtilityCost.setIndividualShare(cost.getPrice() / apartments.size() / daysInYear * days);
                        break;
                }
            }
            finalizedUtilityStatement.setIndividualCost(finalizedUtilityStatement.getFinalizedUtilityCosts().stream().mapToDouble(FinalizedUtilityCost::getIndividualShare).sum());
            finalizedUtilityStatement.setPaymentInAdvance(rentService.calculateAdvancePayment(contract, tempStartDate, tempEndDate));
            finalizedUtilityStatement.setDifference(finalizedUtilityStatement.getPaymentInAdvance() - finalizedUtilityStatement.getIndividualCost());
            finalizedUtilityStatementRepository.save(finalizedUtilityStatement);
        }
        statement.setFinalized(true);
        utilityStatementRepository.save(statement);
    }

    private double calculateTotalPersonMonths(RentalContract rentalContract, LocalDate startDate, LocalDate endDate) {
        LocalDate tempStartDate = rentalContract.getStartDate();
        LocalDate tempEndDate = rentalContract.getEndDate();
        if (tempStartDate.isBefore(startDate) ||
                tempStartDate.isEqual(startDate)) {
            tempStartDate = startDate;
        }
        if (tempEndDate == null ||
                tempEndDate.isAfter(endDate) ||
                tempEndDate.isEqual(endDate)) {
            tempEndDate = endDate;
        }
        return Period.between(tempStartDate, tempEndDate).getMonths() * rentalContract.getTenants().size();
    }
}
