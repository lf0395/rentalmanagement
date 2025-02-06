package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "utility_cost")
public class UtilityCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private BillingType billingType;
    private LocalDate date;
    private boolean isBillable;
    @ManyToOne
    @JoinColumn(name = "utility_statement_id")
    private UtilityStatement utilityStatement;
    @OneToMany(mappedBy = "utilityCost", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BillingType getBillingType() {
        return billingType;
    }

    public void setBillingType(BillingType billingType) {
        this.billingType = billingType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isBillable() {
        return isBillable;
    }

    public void setBillable(boolean billable) {
        isBillable = billable;
    }

    public UtilityStatement getUtilityStatement() {
        return utilityStatement;
    }

    public void setUtilityStatement(UtilityStatement utilityStatement) {
        this.utilityStatement = utilityStatement;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
