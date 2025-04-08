package com.marcosavard.commons.math.trigonometry;

import com.marcosavard.commons.math.SafeMath;

public class Angle implements Comparable<Angle> {
  private static final String DEGREE_SIGN = "\u00B0";
  private double rads;

  public enum Unit {
    DEG, RAD
  };

  public enum Category {
    ZERO, ACUTE, RIGHT, OBTUSE, FLAT, REFLEX, UNKNOWN
  }

  public static Angle of(double value, Unit unit) {
    Angle angle;

    if (unit == Unit.DEG) {
      value = SafeMath.range(value, 0, 360);
      angle = new Angle(Math.toRadians(value));
    } else {
      value = SafeMath.range(value, 0, 2 * Math.PI);
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
      equal = SafeMath.equal(otherAngle.rads(), this.rads(), 0.001);
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

  @Override
  public int compareTo(Angle other) {
    int comparison = (int) (rads * 1000) - (int) (other.rads * 1000);
    return comparison;
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

  public boolean isZero() {
    double delta = Math.abs(this.rads);
    boolean zero = (delta < 0.001);
    return zero;
  }

  public boolean isAcute() {
    boolean acute = (this.rads < Math.PI / 2);
    return acute;
  }

  public boolean isRight() {
    double delta = Math.abs(this.rads - Math.PI / 2);
    boolean right = (delta < 0.001);
    return right;
  }

  public boolean isObtuse() {
    boolean obtuse = (this.rads > Math.PI / 2) && (this.rads < Math.PI);
    return obtuse;
  }

  public boolean isFlat() {
    double delta = Math.abs(this.rads - Math.PI);
    boolean right = (delta < 0.001);
    return right;
  }

  public boolean isReflex() {
    boolean reflex = (this.rads > Math.PI);
    return reflex;
  }

  public Category getCategory() {
    Category category = Category.UNKNOWN;

    if (isZero()) {
      category = Category.ZERO;
    } else if (isAcute()) {
      category = Category.ACUTE;
    } else if (isRight()) {
      category = Category.RIGHT;
    } else if (isFlat()) {
      category = Category.FLAT;
    } else if (isObtuse()) {
      category = Category.OBTUSE;
    } else if (isReflex()) {
      category = Category.REFLEX;
    }

    return category;
  }
}
