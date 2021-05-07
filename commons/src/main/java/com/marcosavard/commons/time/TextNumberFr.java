package com.marcosavard.commons.time;

import java.util.Locale;

public class TextNumberFr extends TextNumber {
	private static final String[] UNITS = new String[] { //
			"", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf",
			"dix", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"
	}; 
		
	private static final String[] TENS = new String[] { //
			"", "dix", "vingt", "trente", "quarante", "cinquante", "soixante", "soixante-dix", "quatre-vingts", "quatre-vingts-dix"
	}; 
	
	protected TextNumberFr(long value) {
		super(value, Locale.FRENCH);
	}
	
	@Override
	public String toString() { 
		String str = (value == 0) ? "zero" : nonZero(value); 
		return str; 
	}
	
	private String nonZero(long value) {
		long positive = Math.abs(value);
		double signum = Math.signum((double)value); 
		
		int bilions = (int)((positive / 1000_000_000) % 1000);
		int milions = (int)((positive / 1000_000) % 1000);
		int thousands = (int)((positive / 1000) % 1000);
		int units = (int)(positive % 1000);
		
		String text = (bilions == 0) ? "" : lessThan1000(bilions, true) + " " + bilionString(bilions) + " ";  
		text += (milions == 0) ? "" : lessThan1000(milions, true) + " " + milionString(milions) + " ";  
		text += (thousands == 0) ? "" : lessThan1000(thousands, false) + " " + thousandString(thousands) + " ";  
		text += lessThan1000(units, true);
		text = (signum < 0) ? "moins " + text : text;
		text = text.trim().replaceAll(" +", " "); //remove duplicated blanks
		
		return text; 
	}

	private String lessThan1000(int value, boolean returnOne) {
		int hundreds = (value / 100) % 100;
		int units = value % 100;
		
		String text = (hundreds == 0) ? "" : lessThan100(hundreds, false) + " " + hundredString(hundreds); 
		text += " " + lessThan100(units, returnOne);
		
		return text;
	}
	
	private String lessThan100(int value, boolean returnOne) {
		String units, tens, text = ""; 
		value = (value == 1) && ! returnOne ? 0 : value; 
		
		if ((value < 20) || (value > 60)) {  
			units = UNITS[value % 20]; 
			tens = (value < 20) ? "" : TENS[(value / 20) * 2]; 
		} else { 
			units = UNITS[value % 10]; 
			tens = TENS[value / 10]; 
		}
		
		text = tens;
		text += (!tens.isEmpty() && !units.isEmpty() ) ? "-" : ""; 
		text += (! tens.isEmpty() && units.equals("un") ) ? "et-" : "";  
		text += units;
		
		return text;
	}
	
	private String bilionString(int bilions) {
		String str = (bilions <= 1) ? "milliard" : "milliards";
		return str;
	}
	
	private String milionString(int milions) {
		String str = (milions <= 1) ? "million" : "millions";
		return str;
	}
	
	private String thousandString(int thousands) {
		String str = (thousands <= 1) ? "mille" : "milles";
		return str;
	}
	
	private String hundredString(int hundreds) {
		String str = (hundreds <= 1) ? "cent" : "cents";
		return str;
	}

}
