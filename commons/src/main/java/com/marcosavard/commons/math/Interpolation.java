package com.marcosavard.commons.math;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private List<Double> xs = new ArrayList<Double>(); 
	private List<Double> ys = new ArrayList<Double>(); 
	
	public Interpolation() {
		xs.add(0.0); 
		ys.add(0.0); 
	}
	
	public Interpolation(double x, double y) {
		xs.add(x); 
		ys.add(y); 
	}
	
	public void define(double x, double y) {
		xs.add(x); 
		ys.add(y); 
	}

	public double interpolate(double x) {
		int i0 = findIndexOfValueBelow(xs, x);
		int i1 = findIndexOfValueAfter(xs, x);
		
		i0 = (i0 == xs.size() -1 ) ? xs.size() - 2 : i0;
		i1 = (i1 == 0) ? 1 : i1; 
		
		double x0 = xs.get(i0); 
		double x1 = xs.get(i1); 
		double dx = x1 - x0;
		
		double y0 = ys.get(i0); 
		double y1 = ys.get(i1); 
		double dy = y1 - y0;
		
		double y = ((x - x0) / dx) * dy + y0;
		return y;
	}
	
	//
	// private methods
	//

	private int findIndexOfValueBelow(List<Double> list, double p) {
		double value = list.stream().filter(e -> e <= p).mapToDouble(d -> d).max().orElse(list.get(0));
		return list.indexOf(value); 
	}
	
	private int findIndexOfValueAfter(List<Double> list, double p) {
		double value = list.stream().filter(e -> e >= p).mapToDouble(d -> d).min().orElse(list.get(list.size()-1));
		return list.indexOf(value); 
	}

}
