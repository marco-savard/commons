package com.marcosavard.common.math;

/**
 * A class that converts numbers to roman numeral. 
 * 
 * @author Marco
 *
 */
public class RomanNumeral {
	 private final static int[] NUMBERS = { 1000,  900,  500,  400,  100,   90,  
         50,   40,   10,    9,    5,    4,    1 };
	 private final static String[] LETTERS = { "M",  "CM",  "D",  "CD", "C",  "XC",
         "L",  "XL",  "X",  "IX", "V",  "IV", "I" };
	 private int _number;

	/**
	 * Create a roman numeral whose toString() is its textual representation. 
	 * For instance, System.out.println(new RomanNumeral(10)) prints "X". 
	 *  
	 * @param number
	 */
	public RomanNumeral(int number) {
		if (number < 1)
            throw new IllegalArgumentException("must be positive : " + number);
         if (number > 3999)
            throw new IllegalArgumentException("must be 3999 or less : " + number);
         
		_number = number;
	}

	@Override
	public String toString() {
		String romanNumeral = "";  
        int n = _number;    // n, part of num that still has to be converted
        for (int i = 0; i < NUMBERS.length; i++) {
           while (n >= NUMBERS[i]) {
        	   romanNumeral += LETTERS[i];
              n -= NUMBERS[i];
           }
        }
        
        return romanNumeral;
	}
}
