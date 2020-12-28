package com.marcosavard.commons.math;

import java.text.MessageFormat;
import java.util.List;

public class FactorizationDemo {

	public static void main(String[] args) {
		//print the factors of 60
		int number = 60;
		List<Integer> factors = Factorization.getFactorsOf(number); 
		System.out.println(MessageFormat.format("  factors of {0} : {1}", number, factors)); 
		System.out.println(); 
		
		//print prime numbers in the range 0-100 
		for (int i=1; i<100; i++) {
			if (Factorization.isPrime(i)) {
				String s = String.format("%d", i); 
				System.out.println(MessageFormat.format("  {0} is prime ", s)); 
			}
		}
	}

}
