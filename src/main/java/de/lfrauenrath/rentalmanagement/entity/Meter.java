package de.lfrauenrath.rentalmanagement.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "meter")
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number")
    private String number;
    @Column(name = "type")
    @Enumerated
    private MeterType type;
    @OneToMany(mappedBy = "meter")
    private List<MeterValue> meterValueList;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rental_property_id")
    private RentalProperty rentalProperty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public MeterType getType() {
        return type;
    }

    public void setType(MeterType type) {
        this.type = type;
    }

    public List<MeterValue> getMeterValueList() {
        return meterValueList;
    }

    public void setMeterValueList(List<MeterValue> meterValueList) {
        this.meterValueList = meterValueList;
    }

    public RentalProperty getRentalProperty() {
        return rentalProperty;
    }

    public void setRentalProperty(RentalProperty rentalProperty) {
        this.rentalProperty = rentalProperty;
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
