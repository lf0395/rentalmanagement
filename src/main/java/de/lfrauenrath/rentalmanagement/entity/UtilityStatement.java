package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "utility_statement")
public class UtilityStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int year;
    @ManyToOne
    @JoinColumn(name = "rental_property_id")
    private RentalProperty rentalProperty;
    private double totalCost;
    @OneToMany(mappedBy = "utilityStatement", cascade = CascadeType.ALL)
    private List<UtilityCost> utilityCosts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public RentalProperty getRentalProperty() {
        return rentalProperty;
    }

    public void setRentalProperty(RentalProperty rentalProperty) {
        this.rentalProperty = rentalProperty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<UtilityCost> getUtilityCosts() {
        return utilityCosts;
    }

    public void setUtilityCosts(List<UtilityCost> utilityCosts) {
        this.utilityCosts = utilityCosts;
    }
}
