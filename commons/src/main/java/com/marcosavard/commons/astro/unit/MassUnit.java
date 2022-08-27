package com.marcosavard.commons.astro.unit;

public enum MassUnit implements UnitConstant {
    G("g", 0.001),
    KG("kg", 1),
    EARTH("earth", EARTH_MASS);

    private final String name;
    private final double kilograms;

    private MassUnit(String name, double kilograms) {
        this.name = name;
        this.kilograms = kilograms;
    }

    public double toKilograms() {
        return kilograms;
    }

    static interface MassConstant {

    }
}
