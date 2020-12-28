package com.marcosavard.commons.math;

import java.text.MessageFormat;

import com.marcosavard.commons.math.Optimization.Category;
import com.marcosavard.commons.math.Optimization.Formula;

//https://brownmath.com/calc/optimiz.htm
public class OptimizationDemo {
	public static void main(String[] args) {
		final double volume = 355.0; 
		Formula formula = new CylinderFormula(volume); 
		Optimization optimization = new Optimization(formula, Category.MINIMALIZE, 64); 
		double radius = optimization.optimize(0.0, 10.0); 
		double height = volume / (Math.PI * radius * radius); 
		double area = formula.compute(radius); 
		
		String msg = MessageFormat.format("A can of 355 ml that has radius={0} cm and height={1} cm has area={2} cm2 (optimal dimensions)", 
			radius, height, area); 
		System.out.println(msg); 
	}

	
	private static class CylinderFormula implements Formula {
		private double volume; 
		
		CylinderFormula(double volume) {
			this.volume = volume; 
		}
		
		public double compute(double radius) {
			double area = (2 * Math.PI * radius * radius) + (2 * volume / radius);
			return area;
		}
	}
}
