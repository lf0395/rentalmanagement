package de.lfrauenrath.rentalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MeterType {
    WATER("Wasser"),
    ELECTRICITY("Strom"),
    HEATING("Heizung");

    private final String label;

    MeterType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
