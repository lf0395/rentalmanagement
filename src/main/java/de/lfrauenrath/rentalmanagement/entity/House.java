package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "house")
public class House extends RentalProperty {
    private double landSize;
    private double livingArea;
    private int roomCount;
    private boolean isEntireHouseRented;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private List<Apartment> apartments;

    public double getLandSize() {
        return landSize;
    }

    public void setLandSize(double landSize) {
        this.landSize = landSize;
    }

    public double getLivingArea() {
        this.livingArea = this.apartments.stream().mapToDouble(Apartment::getLivingArea).sum();
        return livingArea;
    }

    public void setLivingArea(double livingArea) {
        this.livingArea = livingArea;
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

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

}
