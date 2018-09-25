package com.marcosavard.common.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A class that factorizes a number. 
 * 
 * @author Marco
 *
 */
public class Factorization {
	
	/**
	 * Returns the list of factors of a number. For instance, 
	 * getFactorsOf(12) returns [1, 2, 3, 4, 6, 12].
	 * 
	 * @param number to factorize
	 * @return the list of factors
	 */
	public static List<Integer> getFactorsOf(int number) {
        int limit = (int) Math.ceil(Math.sqrt(number));
        Set<Integer> set = new TreeSet<>(); 

        for (int i = 1; i <= limit; i++) {
            if (number % i == 0) {
            	set.add(i); 
            	set.add(number / i); 
            }
        }
        
        List<Integer> factors = new ArrayList<>(); 
        factors.addAll(set); 
        return factors;
    }

	public static boolean isPrime(int number) {
		List<Integer> factors = Factorization.getFactorsOf(number); 
		//is prime if factors are 1 and the number itself
		boolean prime = factors.size() == 2;
		return prime;
	}

}
