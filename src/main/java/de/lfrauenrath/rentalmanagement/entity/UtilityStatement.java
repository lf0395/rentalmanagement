package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utility_statement")
public class UtilityStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "year")
    private int year;
    @ManyToOne
    @JoinColumn(name = "rental_property_id")
    private RentalProperty rentalProperty;
    @Column(name = "total_cost")
    private double totalCost;
    @OneToMany(mappedBy = "utilityStatement", cascade = CascadeType.ALL)
    private List<UtilityCost> utilityCosts = new ArrayList<>();
    @Column(name = "is_finalized")
    private boolean isFinalized;

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
        this.totalCost = this.utilityCosts.stream().mapToDouble(UtilityCost::getPrice).sum();
        return this.totalCost;
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

    public boolean isFinalized() {
        return isFinalized;
    }

    public void setFinalized(boolean finalized) {
        isFinalized = finalized;
    }

    public void setRentalPropertyId(Long id) {
        if (id != null) {
            this.rentalProperty = new Apartment();
            this.rentalProperty.setId(id);
        } else {
            this.rentalProperty = null;
        }
    }
}
