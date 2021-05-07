package com.marcosavard.commons.time;

import java.util.Locale;

public class TextNumberEn extends TextNumber {
	private static final String[] UNITS = new String[] { //
			"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
			"ten", "eleven", "twelve", "thirteen", "forteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
	}; 
		
	private static final String[] TENS = new String[] { //
			"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
	}; 
	
	protected TextNumberEn(long value) {
		super(value, Locale.ENGLISH);
	}
	
	@Override
	public String toString() { 
		int millions = (int)((value / 1000_000) % 1000_000);
		int thousands = (int)((value / 1000) % 1000);
		int units = (int)(value % 1000);
		
		String text = (millions == 0) ? "" : lessThan1000(millions) + " " + "million" + " ";  
		text += (thousands == 0) ? "" : lessThan1000(thousands) + " " + "thousand" + " ";  
		text += lessThan1000(units);
		
		return text; 
	}
	
	private String lessThan1000(int value) {
		int hundreds = (value / 100) % 100;
		int units = value % 100;
		
		String text = (hundreds == 0) ? "" : lessThan100(hundreds) + " " + "hundred"; 
		text += " " + lessThan100(units);
		
		return text;
	}

	private String lessThan100(int value) {
		String text = ""; 
		
		if (value < 20) {  
			text = UNITS[value]; 
		} else { 
			int tens = value / 10;
			text = TENS[tens] + "-" + UNITS[value % 10]; 
		}
		
		return text;
	}

}
