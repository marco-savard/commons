package com.marcosavard.commons.text.format;

import java.util.Locale;

/**
 * Format ordinal number (1st, 2nd, ..) according a given locale. 
 * 
 * @author Marco
 *
 */
public class OrdinalFormatter {
	private Locale locale; 
	
	/**
	 * Create the formatter in the given local. If no locale is
	 * specified, use Locale.getDefault(). 
	 * 
	 * @param locale format in this locale
	 */
	public OrdinalFormatter(Locale locale) {
		this.locale = locale; 
	}
	
	public OrdinalFormatter() {
		this(Locale.getDefault());
	}

	/**
	 * Format the ordinal of this number. For instance format(2) 
	 * returns "2nd". 
	 * 
	 * @param number to format
	 * @return a textual representation of the ordinal number
	 */
	public String format(int number) {
		String formatted; 
		
		if (locale.equals(Locale.FRENCH)) {
			formatted = formatFr(number); 
		} else {
			formatted = formatEn(number); 
		}
		
		return formatted;  
	}

	//
	// private method
	//
	private String formatEn(int cardinal) {
		String ordinal; 
		
		if (((cardinal % 10) == 1) && ((cardinal % 100) != 11)) {
			ordinal = cardinal + "st"; 
		} else if (((cardinal % 10) == 2) && ((cardinal % 100) != 12)) {
			ordinal = cardinal + "nd"; 
		} else if (((cardinal % 10) == 3) && ((cardinal % 100) != 13)) {
			ordinal = cardinal + "rd"; 
		} else {
			ordinal = cardinal + "th"; 
		}
		
		return ordinal;
	}
	
	private String formatFr(int cardinal) {
		String ordinal; 
		
		if (cardinal == 1) {
			ordinal = cardinal + "er"; 
		} else {
			ordinal = cardinal + "e"; 
		}
		
		return ordinal;
	}

}
