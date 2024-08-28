package com.marcosavard.commons.astro.unit;

public class Length {
  private double value;
  private LengthUnit unit;

  public static Length of(double value, LengthUnit unit) {
    return new Length(value, unit);
  }

  private Length(double value, LengthUnit unit) {
    this.value = value;
    this.unit = unit;
  }

  public double toMeters() {
    return value * unit.toMeters();
  }
}
