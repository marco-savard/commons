package com.marcosavard.commons.time;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JulianDate {

	public static int toJulianDate(Date date) {
		int year = Integer.parseInt(new SimpleDateFormat("yy").format(date)); 
		int day = Integer.parseInt(new SimpleDateFormat("DDD").format(date)); 
		int julianDate = ((year + 100) * 1000) + day;
		return julianDate;
	}

	public static LocalDate toLocalDate(int julianDate) {
		int dayOfYear = julianDate % 1000;
		int year = ((julianDate - dayOfYear) / 1000) + 1900;
		LocalDate date = LocalDate.ofYearDay(year, dayOfYear); 
		return date;
	}
	
	public static Date toDate(int julianDate) {
		LocalDate localDate = toLocalDate(julianDate); 
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return date;
	}
}
