package com.marcosavard.commons.time;

import java.util.Date;

/**
 * Find a Julian day. 
 * 
 * Ref: http://en.wikipedia.org/wiki/Julian_day
 * 
 * @author Marco
 *
 */
public class JulianDay {
	
	/** Convert a standard date into a Julian day
	 *  
	 * @param date a given date
	 * @return the Julian day
	 */
	public static int toJulianDay(Date date) {
		int year = 1900 + date.getYear(); 
	    int month = date.getMonth() + 1;  
	    int day = date.getDate(); 
	    
	    int a = (14 - month) / 12;
	    int y = year + 4800 - a;
        int m = month + 12 * a - 3;
	    int jdn = day + (153 * m + 2)/5 + 365*y + y/4 - y/100 + y/400 - 32045;
      
	    return jdn;
	}
}
