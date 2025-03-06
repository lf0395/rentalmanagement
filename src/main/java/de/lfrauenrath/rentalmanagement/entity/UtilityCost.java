package de.lfrauenrath.rentalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "utility_cost")
public class UtilityCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "billing_type")
    private BillingType billingType;
    @Column(name = "is_billable")
    private boolean isBillable;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double consumption;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MeterType meterType;
    @ManyToOne
    @JoinColumn(name = "utility_statement_id")
    @JsonIgnore
    private UtilityStatement utilityStatement;
    @OneToMany(mappedBy = "utilityCost", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

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

    public boolean isBillable() {
        return isBillable;
    }

    public void setBillable(boolean billable) {
        isBillable = billable;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
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

    public void setUtilityStatementId(Long id) {
        if (id != null) {
            this.utilityStatement = new UtilityStatement();
            this.utilityStatement.setId(id);
        } else {
            this.utilityStatement = null;
        }
    }
}
