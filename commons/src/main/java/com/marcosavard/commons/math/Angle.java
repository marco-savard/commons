package com.marcosavard.commons.math;

// TODO isRight, isAigu, isObtu
// TODO difference(), isLess(), Comparable
public class Angle {
  private static final String DEGREE_SIGN = "\u00B0";
  private double rads;

  public enum Unit {
    DEGREES, RADS
  };

  public static Angle of(double value, Unit unit) {
    Angle angle;

    if (unit == Unit.DEGREES) {
      value = Maths.valueInRange(value, 360);
      angle = new Angle(Math.toRadians(value));
    } else {
      value = Maths.valueInRange(value, 2 * Math.PI);
      angle = new Angle(value);
    }

    return angle;
  }

  private Angle(double rads) {
    this.rads = rads;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Angle) {
      Angle otherAngle = (Angle) other;
      equal = Maths.equal(otherAngle.rads(), this.rads());
    }
    return equal;
  }

  @Override
  public int hashCode() {
    return (int) this.rads;
  }

  @Override
  public String toString() {
    String str = Double.toString(degrees()) + DEGREE_SIGN;
    return str;
  }

  public double degrees() {
    return Math.toDegrees(rads);
  }

  public double rads() {
    return rads;
  }

  public Angle addTo(Angle other) {
    double sum = rads + other.rads;
    return Angle.of(sum, Unit.RADS);
  }

}
