package com.marcosavard.commons.astro.unit;

public interface Constant {

    public static final double G = 6.673e-11; // in  N•m2/kg2

    public static final double EARTH_ORBIT_ECCENTRICITY = 0.0167086;

    public static final double PI2 = Math.PI * Math.PI;

    public static final Length EARTH_RADIUS_LENGTH = Length.of(6.373e6, LengthUnit.M);

    public static final Length SUN_RADIUS_VALUE = Length.of(696342e8, LengthUnit.M);

    public static final Length ASTRONOMICAL_UNIT = Length.of(1.495978707e11, LengthUnit.M);

    public static final Length LIGHT_YEAR = Length.of(9.4607e15, LengthUnit.M);

    public static final Mass EARTH_MASS = Mass.of(5.98e24, MassUnit.KG);

    public static final Mass SUN_MASS_VALUE = Mass.of(1.98847e30, MassUnit.KG);

}
