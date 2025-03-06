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
    @JoinColumn(name = "rental_contract_id")
    private RentalContract rentalContract;
    @OneToMany(mappedBy = "finalizedStatement", cascade = CascadeType.ALL)
    private List<FinalizedUtilityCost> finalizedUtilityCosts;
    @Column(name = "individual_cost")
    private double individualCost;
    @Column(name = "breakdown")
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

    public RentalContract getRentalContract() {
        return rentalContract;
    }

    public void setRentalContract(RentalContract rentalContract) {
        this.rentalContract = rentalContract;
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
