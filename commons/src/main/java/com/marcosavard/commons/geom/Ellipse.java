package com.marcosavard.commons.geom;

public class Ellipse extends Shape {
	double centerX, centerY, radiusX, radiusY;
	
	public static Ellipse createEllipsis(double centerX, double centerY, double radiusX, double radiusY) {
		return new Ellipse(centerX, centerY, radiusX, radiusY); 
	}
	
	public static Ellipse createCircle(double centerX, double centerY, double radius) {
		return new Ellipse(centerX, centerY, radius, radius); 
	}
	
	public Ellipse(double centerX, double centerY, double radiusX, double radiusY) {
		this.centerX = centerX;
		this.centerY = centerY; 
		this.radiusX = radiusX;
		this.radiusY = radiusY; 
	}
	
	public double getCenterX() {
		return centerX;
	}
	
	public double getCenterY() {
		return centerY;
	}
	
	public double getRadiusX() {
		return radiusX;
	}
	
	public double getRadiusY() {
		return radiusY;
	}
	
	public double getWidth() {
		return radiusX * 2;
	}
	
	public double getHeight() {
		return radiusY * 2;
	}

	@Override
	public double getArea() {
		double area = Math.PI * radiusX * radiusY;
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
        return (normx * normx + normy * normy) < 0.25;
    }

	@Override
	public Rectangle getBounds() {
		double x = centerX - radiusX;  
		double y = centerY - radiusY; 
		Rectangle bounds = new Rectangle(x, y, getWidth(), getHeight()); 
		return bounds;
	}

	
}
