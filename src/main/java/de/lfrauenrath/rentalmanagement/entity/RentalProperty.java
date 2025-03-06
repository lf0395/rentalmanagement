package de.lfrauenrath.rentalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "rental_property")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type") // Fügt "type" ins JSON hinzu
@JsonSubTypes({
        @JsonSubTypes.Type(value = House.class, name = "Haus"),
        @JsonSubTypes.Type(value = Apartment.class, name = "Wohnung")
})
public abstract class RentalProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street")
    private String street;
    @Column(name = "house_number")
    private String houseNumber;
    @Column(name = "city")
    private String city;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "living_area")
    private double livingArea;
    @OneToMany(mappedBy = "rentalProperty")
    @JsonIgnore
    private List<Meter> meterList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(double livingArea) {
        this.livingArea = livingArea;
    }

    public List<Meter> getMeterList() {
        return meterList;
    }

    public void setMeterList(List<Meter> meterList) {
        this.meterList = meterList;
    }

    @JsonProperty("fullAddress")
    public String getFullAddress() {
        if (street == null || houseNumber == null || zipCode == null || city == null) {
            return "";
        }
        return street + " " + houseNumber + ", " + zipCode + " " + city;
    }
}
