package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental_contract")
public class RentalContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rental_contract_id")
    private List<Rent> rents = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "rental_property_id")
    private RentalProperty rentalProperty;
    @OneToMany
    @JoinColumn(name = "rental_contract_id")
    private List<Person> tenants = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "finalized_utility_statement_id")
    private List<FinalizedUtilityStatement> finalizedUtilityStatements = new ArrayList<>();


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

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
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

    public List<FinalizedUtilityStatement> getFinalizedUtilityStatements() {
        return finalizedUtilityStatements;
    }

    public void setFinalizedUtilityStatements(List<FinalizedUtilityStatement> finalizedUtilityStatements) {
        this.finalizedUtilityStatements = finalizedUtilityStatements;
    }

    public void setRentalPropertyId(Long id) {
        if (id != null) {
            this.rentalProperty = new Apartment();
            this.rentalProperty.setId(id);
        } else {
            this.rentalProperty = null;
        }
    }

    public void setPersons(List<Long> persons) {
        if (persons != null) {
            this.tenants = persons.stream().map(personId -> {
                Person person = new Person();
                person.setId(personId);
                return person;
            }).toList();
        } else {
            this.tenants = null;
        }
    }
}
