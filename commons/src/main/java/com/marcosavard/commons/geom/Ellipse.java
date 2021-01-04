package com.marcosavard.commons.geom;

// immutable ellipse, purely mathematical and independent from awt
public class Ellipse extends Shape {
  private final double x, y, width, height;

  public static Ellipse of(double centerX, double centerY, double radiusX, double radiusY) {
    double x = centerX - radiusX;
    double y = centerY - radiusY;
    double w = radiusX * 2;
    double h = radiusY * 2;
    return new Ellipse(x, y, w, h);
  }

  public static Ellipse circleOf(double centerX, double centerY, double radius) {
    double x = centerX - radius;
    double y = centerY - radius;
    double w = radius * 2;
    double h = radius * 2;
    return new Ellipse(x, y, w, h);
  }

  private Ellipse(double x, double y, double w, double h) {
    this.x = x;
    this.y = y;
    this.width = w;
    this.height = h;
  }

  public double getCenterX() {
    return x + (width / 2);
  }

  public double getCenterY() {
    return y + (height / 2);
  }

  public double getRadiusX() {
    return (width / 2);
  }

  public double getRadiusY() {
    return (height / 2);
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  @Override
  public double getArea() {
    double area = Math.PI * getRadiusX() * getRadiusY();
    return area;
  }

  @Override
  public boolean contains(double x, double y) {
    // Normalize the coordinates compared to the ellipse
    // having a center at 0,0 and a radius of 0.5.
    double ellw = getWidth();
    if (ellw <= 0.0) {
      return false;
    }
    double normx = (x + getRadiusX() - getCenterX()) / ellw - 0.5;
    double ellh = getHeight();
    if (ellh <= 0.0) {
      return false;
    }
    double normy = (y + getRadiusY() - getCenterY()) / ellh - 0.5;
    boolean contained = (normx * normx + normy * normy) < 0.25;
    return contained;
  }

  @Override
  public Rectangle getBounds() {
    double x = getCenterX() - getRadiusX();
    double y = getCenterY() - getRadiusY();
    Rectangle bounds = Rectangle.of(x, y, getWidth(), getHeight());
    return bounds;
  }


}
