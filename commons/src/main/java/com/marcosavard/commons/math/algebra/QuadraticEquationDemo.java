package com.marcosavard.commons.math.algebra;

import java.util.Arrays;

import com.marcosavard.commons.math.arithmetic.ComplexNumber;

public class QuadraticEquationDemo {

	public static void main(String[] args) {
		double a = 2.3, b = 4, c = 5.6;
		QuadraticEquation equation = QuadraticEquation.of(a, b, c); 
		ComplexNumber[] solutions = equation.getRoots();
		System.out.println(equation);
		System.out.println("  roots : " + Arrays.toString(solutions));
	}

}