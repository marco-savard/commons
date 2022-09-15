package com.marcosavard.commons.math.algebra;

import java.text.MessageFormat;

import com.marcosavard.commons.math.arithmetic.ComplexNumber;

public class QuadraticEquation {
	private double a, b, c; 
	private double determinant;
	
	public static QuadraticEquation of(double a, double b, double c) {
		QuadraticEquation equation = new QuadraticEquation(a, b, c);
		return equation;
	}
	
	private QuadraticEquation(double a, double b, double c) {
		this.a = a; 
		this.b = b; 
		this.c = c; 
		
		// calculate the determinant (b^2 - 4ac)
		this.determinant = (b * b) - (4 * a * c); 
	}
	
	@Override
	public String toString() {
		String msg = MessageFormat.format("{0}x\u00B2 + {1}x + {2} = 0", a, b, c); 
		return msg; 
	}

	public double getDeterminant() {
		return determinant;
	}
	
	public ComplexNumber[] getRoots() {
		ComplexNumber[] roots = new ComplexNumber[2]; 
		
		if (determinant > 0) { 
			// two real and distinct roots
			roots[0] = ComplexNumber.of((-b + Math.sqrt(determinant)) / (2 * a), 0);
			roots[1] = ComplexNumber.of((-b - Math.sqrt(determinant)) / (2 * a), 0);
		} else if (determinant == 0) {
			roots[0] = roots[1] = ComplexNumber.of(-b / (2 * a), 0);
		} else { 
			// roots are complex number and distinct
		    double real = -b / (2 * a);
		    double imaginary = Math.sqrt(-determinant) / (2 * a);		    
		    roots[0] = ComplexNumber.of(real, imaginary); 
		    roots[1] = ComplexNumber.of(real, - imaginary); 
		}
		
		return roots;
	}


}
