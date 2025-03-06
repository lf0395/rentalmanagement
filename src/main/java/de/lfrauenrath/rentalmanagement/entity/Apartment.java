package de.lfrauenrath.rentalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("APARTMENT")
public class Apartment extends RentalProperty {

    @Column(name = "room_count")
    private int roomCount;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = true)
    @JsonIgnore // Verhindert das vollständige Haus-Objekt in der Response
    private House house;

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @JsonProperty("houseId") // Fügt die houseId explizit zur JSON-Antwort hinzu
    public Long getHouseId() {
        return house != null ? house.getId() : null;
    }

    public void setHouseId(Long houseId) {
        if (houseId != null) {
            this.house = new House();
            this.house.setId(houseId);
        } else {
            this.house = null;
        }
    }

    @Override
    @JsonProperty("fullAddress")
    public String getFullAddress() {
        if (house != null) {
            return house.getFullAddress() + " - " + name;
        }
        return super.getFullAddress();
    }
}
