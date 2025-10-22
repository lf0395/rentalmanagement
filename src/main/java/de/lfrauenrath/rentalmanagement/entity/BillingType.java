package de.lfrauenrath.rentalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BillingType {
    SQUARE_METERS("Quadratmeter"),
    PERSONS("Personen"),
    CONSUMPTION("Verbrauch"),
    FLAT_RATE("Einheit");

    private final String label;

    BillingType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
