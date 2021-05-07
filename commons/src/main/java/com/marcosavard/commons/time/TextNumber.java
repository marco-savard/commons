package com.marcosavard.commons.time;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

//textual representation of number
//support cardinal (one, two, ..) and ordinal (first, second, ..)
public class TextNumber {
	protected long value; 

	public static TextNumber of(long value) {
		return of(value, Locale.getDefault()); 
	}
	
	public static TextNumber of(long value, Locale locale) {
		TextNumber number; 
		
		if (locale.getLanguage().equals("en")) { 
			number = new TextNumberEn(value);
		} else if (locale.getLanguage().equals("fr")) { 
			number = new TextNumberFr(value);
		} else {
			number = new TextNumber(value, locale); 
		}
		
		return number;
	}
	
	protected TextNumber(long value, Locale locale) {
		this.value = value;
	}
	
	@Override
	public String toString() { 
		NumberFormat fmt = DecimalFormat.getNumberInstance();
		String str = fmt.format(value);
		return str; 
	}


	

}
