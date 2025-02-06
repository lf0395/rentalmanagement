package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "finalized_utility_statement")
public class FinalizedUtilityStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "utility_statement_id")
    private UtilityStatement utilityStatement;
    @ManyToOne
    @JoinColumn(name = "rental_property_id")
    private RentalProperty rentalProperty;
    @OneToMany(mappedBy = "finalizedStatement", cascade = CascadeType.ALL)
    private List<FinalizedUtilityCost> finalizedUtilityCosts;
    private double individualCost;
    private String breakdown;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UtilityStatement getUtilityStatement() {
        return utilityStatement;
    }

    public void setUtilityStatement(UtilityStatement utilityStatement) {
        this.utilityStatement = utilityStatement;
    }

    public RentalProperty getRentalProperty() {
        return rentalProperty;
    }

    public void setRentalProperty(RentalProperty rentalProperty) {
        this.rentalProperty = rentalProperty;
    }

    public List<FinalizedUtilityCost> getFinalizedUtilityCosts() {
        return finalizedUtilityCosts;
    }

    public void setFinalizedUtilityCosts(List<FinalizedUtilityCost> finalizedUtilityCosts) {
        this.finalizedUtilityCosts = finalizedUtilityCosts;
    }

    public double getIndividualCost() {
        return individualCost;
    }

    public void setIndividualCost(double individualCost) {
        this.individualCost = individualCost;
    }

    public String getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(String breakdown) {
        this.breakdown = breakdown;
    }
}
