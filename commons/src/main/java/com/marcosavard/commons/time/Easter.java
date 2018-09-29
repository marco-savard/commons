package com.marcosavard.commons.time;

import java.time.LocalDate;
import java.util.Date;

/**
 * A class that computes Easter date for a given year, 
 * 
 * @author Marco
 *
 */
public class Easter {

	/**
	 * Compute the Easter date for a given year. 
	 *  
	 * @param year (number of years after common era). 
	 * @return the date of Easter
	 */
	public static Date getEasterDate(int year) {
		DateField fields = computeDateFields(year);
		Date easter = new Date(fields.year-1900, fields.month-1, fields.day); 
		return easter; 
	}
	
	/**
	 * Compute the Easter local date for a given year. 
	 *  
	 * @param year (number of years after common era). 
	 * @return the local date of Easter
	 */
	public static LocalDate getEasterLocalDate(int year) {
		DateField fields = computeDateFields(year);
		LocalDate easter = LocalDate.of(fields.year, fields.month, fields.day); 
		return easter; 
	}
	
	// 
	// private method
	// 
	private static DateField computeDateFields(int year) {
	    int golden = (year % 19) + 1; // E1: metonic cycle of 19 years
	    int century = (year / 100) + 1; // E2: e.g. 1984 was in 20th C 
	    int x = (3 * century / 4) - 12; // E3: leap year correction 
	    int z = ((8 * century + 5) / 25) - 5; // E3: sync with moon's orbit 
	    int d = (5 * year / 4) - x - 10;
	    int epact = (11 * golden + 20 + z - x) % 30; // E5: epact 
	    
	    if ((epact == 25 && golden > 11) || epact == 24)
	      epact++;
	    
	    int n = 44 - epact;
	    n += 30 * (n < 21 ? 1 : 0); // E6 
	    n += 7 - ((d + n) % 7);
	    
	    DateField fields = new DateField(); 
	    fields.year = year;
	    fields.month = (n <= 31) ? 3 : 4; //March or April
	    fields.day = (n <= 31) ? n : n-31; //day of month
	    return fields; 
	}
	
	private static class DateField {
		int year, month, day; 
	}

}
