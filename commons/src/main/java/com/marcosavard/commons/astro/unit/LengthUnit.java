package com.marcosavard.commons.astro.unit;

public enum LengthUnit implements UnitConstant {
    M("m", 1),
    KM("km", 1000),
    EARTH_RADIUS("earthRadius", EARTH_RADIUS_VALUE);

    private final String name;
    private final double meters;

    private LengthUnit(String name, double meters) {
        this.name = name;
        this.meters = meters;
    }

    public double toMeters() {
        return meters;
    }
}