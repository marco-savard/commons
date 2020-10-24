package com.marcosavard.commons.math;

// TODO isRight, isAigu, isObtu
// TODO difference(), isLess(), Comparable
public class Angle {
  private static final String DEGREE_SIGN = "\u00B0";
  private double rads;

  public enum Unit {
    DEG, RAD
  };

  public static Angle of(double value, Unit unit) {
    Angle angle;

    if (unit == Unit.DEG) {
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
    return Angle.of(sum, Unit.RAD);
  }

  public double minus(Angle other) {
    double twoPi = 2 * Math.PI;
    double d = Math.abs(rads - other.rads) % twoPi;
    double r = d > Math.PI ? twoPi - d : d;

    // calculate sign
    int sign = (rads - other.rads >= 0 && rads - other.rads <= Math.PI)
        || (rads - other.rads <= -Math.PI && rads - other.rads >= -twoPi) ? 1 : -1;
    r *= sign;

    double deg = Math.toDegrees(r);
    return deg;
  }

  // 179 is before 180, 359 is before 0
  public boolean isBefore(Angle other) {
    double diff = minus(other);
    boolean isBefore = (diff < 0);
    return isBefore;
  }



}
