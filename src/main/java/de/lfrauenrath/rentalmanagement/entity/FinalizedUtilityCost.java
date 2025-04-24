package de.lfrauenrath.rentalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "finalized_utility_cost")
public class FinalizedUtilityCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "finalized_statement_id")
    @JsonIgnore
    private FinalizedUtilityStatement finalizedStatement;
    @Column(name = "total_cost")
    private double totalCost;
    @Column(name = "individual_share")
    private double individualShare;
    @Column(name = "billing_type")
    @Enumerated
    private BillingType billingType;
    @OneToMany(mappedBy = "finalizedUtilityCost", cascade = CascadeType.ALL)
    private List<FinalizedAttachment> finalizedAttachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

