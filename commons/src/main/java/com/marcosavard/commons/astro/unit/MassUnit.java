package com.marcosavard.commons.astro.unit;

public enum MassUnit implements Constant {
    G("g", 0.001),
    KG("kg", 1),
    EARTH("earth", 5.98e24),

    SUN_MASS_VALUE("sun", 1.98847e30);

    private final String name;
    private final double kilograms;

    private MassUnit(String name, double kilograms) {
        this.name = name;
        this.kilograms = kilograms;
    }

    public double toKilograms() {
        return kilograms;
    }
}
