package de.lfrauenrath.rentalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("HOUSE")
public class House extends RentalProperty {

    @Column(name = "land_size")
    private double landSize;
    @Column(name = "room_count")
    private int roomCount;
    @Column(name = "is_entire_house_rented")
    private boolean isEntireHouseRented;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Apartment> apartments;

    public double getLandSize() {
        return landSize;
    }

    public void setLandSize(double landSize) {
        this.landSize = landSize;
    }

    public double getLivingArea() {
        if (apartments == null) {
            return super.getLivingArea(); // Falls keine Apartments vorhanden sind, gib den gespeicherten Wert zurück
        }
        return this.apartments.stream().mapToDouble(Apartment::getLivingArea).sum();
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public boolean isEntireHouseRented() {
        return isEntireHouseRented;
    }

    public void setEntireHouseRented(boolean entireHouseRented) {
        isEntireHouseRented = entireHouseRented;
    }

    @JsonProperty("apartments")
    public List<Apartment> getApartments() {
        return apartments == null ? new ArrayList<>() : apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

}
