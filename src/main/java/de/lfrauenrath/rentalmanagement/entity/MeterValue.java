package de.lfrauenrath.rentalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "meter_value")
public class MeterValue {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "measure_date")
    private LocalDate measureDate;
    @Column(name = "value")
    private double value;
    @ManyToOne
    @JoinColumn(name = "meter_id")
    @JsonIgnore
    private Meter meter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMeasureDate() {
        return measureDate;
    }

    public void setMeasureDate(LocalDate measureDate) {
        this.measureDate = measureDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }
}
