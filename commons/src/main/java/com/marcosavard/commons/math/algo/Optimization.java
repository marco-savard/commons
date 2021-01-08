package com.marcosavard.commons.math.algo;

public class Optimization {
	public enum Category {MINIMALIZE, MAXIMILIZE}; 
	
	private Formula formula; 
	private Category category;
	private int nbLoops; 
	
	public Optimization(Formula formula, Category category, int nbLoops) {
		this.formula = formula;
		this.category = category;
		this.nbLoops = nbLoops;
	}

	public double optimize(double start, double end) {	
		double param = start; 
		
		for (int i=0; i<nbLoops; i++) { 
			double span = end-start;
			double step = span / nbLoops; 
			double result = optimizeStep(start, end); 
			start = result - step;
			end = result + step; 
			param = (start + end) / 2;
		}
		
		return param;
	}
	
	private double optimizeStep(double start, double end) {
		double span = end-start;
		double step = span / nbLoops; 
		double summit = (category == Category.MINIMALIZE) ? Double.MAX_VALUE : Double.MIN_VALUE; 
		double param = start; 
		
		for (int i=0; i<nbLoops; i++) {
			double start0 = start + (i * step);
			double result = formula.compute(start0); 
			
			if ((category == Category.MINIMALIZE)) {
				param = (result > summit) ? param : start0;
				summit = Math.min(summit, result); 
			} else if ((category == Category.MAXIMILIZE)) {
				param = (result < summit) ? param : start0;
				summit = Math.max(summit, result); 
			}
		}
		
		return param;
	}

	public static interface Formula {
		public double compute(double parameter); 
	}

}
