package com.marcosavard.commons.astro.unit;

public class Mass {
    private double value;
    private MassUnit unit;


    public static Mass of(double value, MassUnit unit) {
        return new Mass(value, unit);
    }

    private Mass(double value, MassUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double toKg() {
        return value * unit.toKilograms();
    }
}
