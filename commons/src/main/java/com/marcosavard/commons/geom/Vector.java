package com.marcosavard.commons.geom;

import java.text.MessageFormat;

public class Vector {
	public enum AngleUnit {DEGREES, RADS}; 
	private final double x;
	private final double y; 
	
	public static Vector of(double x, double y) {
		return new Vector(x, y); 
	}

	public static Vector ofPolarCoordinates(double length, double angle, AngleUnit unit) {
		double angleInRads = (unit == AngleUnit.RADS) ? angle : angle * Math.PI / 180.0; 
		double x = length * Math.cos(angleInRads); 
		double y = length * Math.sin(angleInRads); 
		return new Vector(x, y); 
	}
	
	private Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() { return x; }
	public double getY() { return y; }
	
	public double getLength() {
		double radius = Math.sqrt(x*x + y*y); 
		return radius;
	}
	
	public double getAngleInRadians() {
		double angle = Math.atan2(y, x); 
		return angle;
	}
	
	public double getAngleInDegrees() {
		double angle = getAngleInRadians() * 180.0 / Math.PI;
		return angle;
	}
	
	public Vector add(Vector that) {
		Vector sum = new Vector(x + that.x, y + that.y); 
		return sum;
	}
	
	public Vector substract(Vector that) {
		Vector difference = new Vector(x - that.x, y - that.y); 
		return difference;
	}
	
	public Vector multiply(double factor) {
		Vector product = new Vector(x * factor, y * factor); 
		return product;
	}
	
	public Vector divide(double factor) {
		Vector quotient = new Vector(x / factor, y / factor); 
		return quotient;
	}
	
	
	@Override
	public String toString() {
		return MessageFormat.format("[{0}, {1}]", x, y); 
	}





}
