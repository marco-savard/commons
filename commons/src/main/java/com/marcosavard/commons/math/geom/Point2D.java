package com.marcosavard.commons.math.geom;

import com.marcosavard.commons.math.SafeMath;

import java.text.MessageFormat;

public class Point2D {
  private double x, y;

  public static Point2D of(double x, double y) {
    return new Point2D(x, y);
  }

  public static Point2D ofPolarCoordinates(double radius, double angle) {
    double x = radius * Math.cos(angle);
    double y = radius * Math.sin(angle);
    return new Point2D(x, y);
  }

  private Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getRadius() {
    double radius = Math.sqrt(x * x + y * y);
    return radius;
  }

  public double getAngle() {
    double angle = Math.atan2(y, x);
    return angle;
  }

  public double distanceFrom(Point2D that) {
    double dx = (that.getX() - x) * (that.getX() - x);
    double dy = (that.getY() - y) * (that.getY() - y);
    return Math.sqrt(dx + dy);
  }

  public double distance2DFrom(Point2D that) {
    double dx = (that.getX() - x) * (that.getX() - x);
    double dy = (that.getY() - y) * (that.getY() - y);
    return dx + dy;
  }

  @Override
  public boolean equals(Object o) {
    boolean equal = false;

    if (o instanceof Point2D) {
      Point2D that = (Point2D) o;
      equal = SafeMath.equal(this.x, that.x, 0.001) && SafeMath.equal(this.y, that.y, 0.001);
    }

    return equal;
  }

  @Override
  public int hashCode() {
    return (int) (x * 7 + y);
  }

  @Override
  public String toString() {
    return MessageFormat.format("(x={0}, y={1})", x, y);
  }
}
