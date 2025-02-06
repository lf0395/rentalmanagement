package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "finalized_utility_cost")
public class FinalizedUtilityCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "finalized_statement_id")
    private FinalizedUtilityStatement finalizedStatement;
    private double totalCost;
    private double individualShare;
    private BillingType billingType;
    @OneToMany(mappedBy = "finalizedUtilityCost", cascade = CascadeType.ALL)
    private List<FinalizedAttachment> finalizedAttachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinalizedUtilityStatement getFinalizedStatement() {
        return finalizedStatement;
    }

    public void setFinalizedStatement(FinalizedUtilityStatement finalizedStatement) {
        this.finalizedStatement = finalizedStatement;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getIndividualShare() {
        return individualShare;
    }

    public void setIndividualShare(double individualShare) {
        this.individualShare = individualShare;
    }

    public BillingType getBillingType() {
        return billingType;
    }

    public void setBillingType(BillingType billingType) {
        this.billingType = billingType;
    }

    public List<FinalizedAttachment> getFinalizedAttachments() {
        return finalizedAttachments;
    }

    public void setFinalizedAttachments(List<FinalizedAttachment> attachments) {
        this.finalizedAttachments = attachments;
    }
}

