package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "apartment")
public class Apartment extends RentalProperty {
    private double livingArea;
    private int roomCount;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    public double getLivingArea() {
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

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
