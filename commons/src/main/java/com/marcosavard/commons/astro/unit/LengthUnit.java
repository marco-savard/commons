package com.marcosavard.commons.astro.unit;

public enum LengthUnit implements Constant {
    M("m", 1),
    KM("km", 1000),
    EARTH_RADIUS("earthRadius", 6.373e6),

    SUN_RADIUS_VALUE("sunRadius", 696342e8),

    ASTRONOMICAL_UNIT("au", 1.495978707e11),

    LIGHT_YEAR("lyr", 9.4607e15);

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
