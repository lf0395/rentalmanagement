package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rental_contract")
public class RentalContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private double coldRent;
    private double warmRent;
    @ManyToOne
    @JoinColumn(name = "rental_property_id")
    private RentalProperty rentalProperty;
    @OneToMany
    @JoinColumn(name = "rental_contract_id")
    private List<Person> tenants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getColdRent() {
        return coldRent;
    }

    public void setColdRent(double coldRent) {
        this.coldRent = coldRent;
    }

    public double getWarmRent() {
        return warmRent;
    }

    public void setWarmRent(double warmRent) {
        this.warmRent = warmRent;
    }

    public RentalProperty getRentalProperty() {
        return rentalProperty;
    }

    public void setRentalProperty(RentalProperty rentalProperty) {
        this.rentalProperty = rentalProperty;
    }

    public List<Person> getTenants() {
        return tenants;
    }

    public void setTenants(List<Person> tenants) {
        this.tenants = tenants;
    }
}
