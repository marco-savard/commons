package com.marcosavard.commons.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A utility class for date-related methods. 
 * 
 * @author Marco
 *
 */
public class Dates {
	public enum Time {BEFORE, AFTER}; 
	
	/**
     * Build a new Date from two Date-typed parameters, the first one
     * representing an actual date (yyyy-MM-dd) and a second one representing
     * the time of of day (hh:mm:ss). For instance, if the first parameter
     * represents 2018-01-31 at 00:00:00, and the second one represents
     * 1970-01-01 at 15:30:00, then builds a Date representing 2018-01-31 at
     * 15:30:00 and returns it.
     * 
     * @param datePart
     *            a Date object in which time-related fields are ignored.
     * @param timePart
     *            a Date object in which date-related fields are ignored. , e.g.
     *            15:36:07 (date-part ignored)
     * @return new date, e.g. 08-06-2016 15:36:07
     */
    public static Date createDate(Date datePart, Date timePart) {
        @SuppressWarnings("deprecation")
        Date date = new Date(datePart.getYear(), datePart.getMonth(), datePart.getDate(),
                timePart.getHours(), timePart.getMinutes(), timePart.getSeconds());
        return date;
    }
    
	/**
	 * Return the number of days between two dates. 
	 *  
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static long daysBetween(Date firstDate, Date secondDate) {
		long deltaMs = Math.abs(secondDate.getTime() - firstDate.getTime());
	    long daysBefore = TimeUnit.DAYS.convert(deltaMs, TimeUnit.MILLISECONDS);
		return daysBefore;
	}
	

    /**
     * Get a relative date (yesterday, last week, etc.) compared to the current
     * date.<br>
     * <br>
     * For instance:<br>
     * <code>
     * &nbsp;&nbsp;Date aMonthAgo = DateHelper.getDate(30, Time.BEFORE);<br>
     * &nbsp;&nbsp;Date aWeekAgo = DateHelper.getDate(7, Time.BEFORE);<br>
     * &nbsp;&nbsp;Date yesterday = DateHelper.getDate(1, Time.BEFORE);<br>
     * &nbsp;&nbsp;Date tomorrow = DateHelper.getDate(1, Time.AFTER);<br>
     * </code>
     * 
     * @param numberOfDays
     *            a positive or negative number of days
     * @return date relative to the current date
     */
    public static Date getDate(int numberOfDays, Time time) {
        LocalDate today = LocalDate.now();
        LocalDate relativeLocalDate = (time == Time.BEFORE) ? today.plusDays(numberOfDays) :  today.minusDays(numberOfDays);
        Date relativeDate = toDate(relativeLocalDate);
        return relativeDate;
    }
    
    //is year a leap year
    public static boolean isLeapYear(int year) {
		 boolean leap = ((year % 4 == 0) && ((year % 400 == 0) || (year % 100 != 0)));
		 return leap;
	}
    
	/**
	 * Convert a Date into a LocalDate instance. 
	 * 
	 * @param date to convert
	 * @return the LocalDate instance
	 */
	public static LocalDate toLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate(); 
	}
	
	/**
	 * Convert a LocalDate into a Date instance. 
	 * 
	 * @param localDate to convert
	 * @return the Date instance
	 */
	public static Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}


	

}
