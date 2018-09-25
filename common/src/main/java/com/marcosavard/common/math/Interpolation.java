package com.marcosavard.common.math;

/**
 * A class that interpolates values from defined values.  For instance, 
 * if we define that y=0 when x=0, and that y=50 when x=100, then 
 * interpolate(50) gives 25. Interpolation can be used to define 
 * unit of measurement converter programmatically.
 * See {@link InterpolationDemo#main(String[])}.
 * 
 * @author Marco
 *
 */
public class Interpolation {
	private double x0, y0, x1, y1;

	public Interpolation() {
		this(0, 0);
	}
	
	public Interpolation(double x, double y) {
		x0 = x;
		y0 = y;
	}
	
	public void define(double x, double y) {
		x1 = x;
		y1 = y;
	}

	public double interpolate(double x) {
		double dx = x1 - x0;
		double dy = y1 - y0;
		double y = ((x - x0) / dx) * dy + y0;
		return y;
	}

}
