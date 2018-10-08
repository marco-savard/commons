package com.marcosavard.commons.geom;

/**
 * An implementation that describes a rectangle, like java.awt.Rectangle, but
 *  1) purely mathematical, does not depend on java.awt, so can be used in GWT
 *    or other environments where dependency to AWT is not allowed.
 *  2) Immutable implementation, so if it is stored in a structure, a client cannot
 *    gain access to this rectangle and change its properties.
 *  3) Add additional methods not implemented in java.awt.Rectangle. 
 * 
 * @author Marco
 *
 */
public class Rectangle extends Shape {
	double x, y, width, height;
	
	public static Rectangle createRectangle(double x, double y, double width, double height) {
		return new Rectangle(x, y, width, height); 
	}
	
	public static Rectangle createSquare(double x, double y, double length) {
		return new Rectangle(x, y, length, length); 
	}
	
	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height; 
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	@Override
	public double getArea() {
		return width * height;
	}

	@Override
	public boolean contains(double x, double y) {
        double x0 = getX();
        double y0 = getY();
        boolean contained = (x >= x0 &&
                y >= y0 &&
                x < x0 + getWidth() &&
                y < y0 + getHeight());
        return contained; 
    }
	
	public boolean contains(double x, double y, double offsetX, double offsetY) {
        double x0 = getX();
        double y0 = getY();

        return (x + offsetX >= x0 &&
                y + offsetY >= y0 &&
                x - offsetX < x0 + getWidth() &&
                y - offsetY < y0 + getHeight() );
    }
	
	public double distance2FromPoint(double x, double y) {
		double x2 = this.x + this.width;
		double y2 = this.y + this.height;
		double xDiff = (x < this.x) ? this.x - x : x - x2;
		double yDiff = (y < this.y) ? this.y - y : y - y2;

	    xDiff = (xDiff < 0) ? 0 : xDiff;
	    yDiff = (yDiff < 0) ? 0 : yDiff;

	    double distance2 = xDiff * xDiff + yDiff * yDiff;
	    return distance2;
	}
	
	public double distanceFromPoint(double x, double y) {
		double distance = Math.sqrt(distance2FromPoint(x, y)); 
		return distance;
	}
	
	@Override
	public Rectangle getBounds() {
		return this;
	}

}
