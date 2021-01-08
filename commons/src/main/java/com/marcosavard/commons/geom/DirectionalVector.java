package com.marcosavard.commons.geom;

import java.text.MessageFormat;

public class DirectionalVector {
	public enum AngleUnit {DEGREES, RADS}; 
	private final double x;
	private final double y; 
	
	public static DirectionalVector of(double x, double y) {
		return new DirectionalVector(x, y); 
	}

	public static DirectionalVector ofPolarCoordinates(double length, double angle, AngleUnit unit) {
		double angleInRads = (unit == AngleUnit.RADS) ? angle : angle * Math.PI / 180.0; 
		double x = length * Math.cos(angleInRads); 
		double y = length * Math.sin(angleInRads); 
		return new DirectionalVector(x, y); 
	}
	
	private DirectionalVector(double x, double y) {
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
	
	public DirectionalVector add(DirectionalVector that) {
		DirectionalVector sum = new DirectionalVector(x + that.x, y + that.y); 
		return sum;
	}
	
	public DirectionalVector substract(DirectionalVector that) {
		DirectionalVector difference = new DirectionalVector(x - that.x, y - that.y); 
		return difference;
	}
	
	public DirectionalVector multiply(double factor) {
		DirectionalVector product = new DirectionalVector(x * factor, y * factor); 
		return product;
	}
	
	public DirectionalVector divide(double factor) {
		DirectionalVector quotient = new DirectionalVector(x / factor, y / factor); 
		return quotient;
	}
	
	
	@Override
	public String toString() {
		return MessageFormat.format("[{0}, {1}]", x, y); 
	}





}
