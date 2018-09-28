package com.marcosavard.commons.time;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class JulianDateDemo {
	
	public static void main(String[] args) {
		DateFormat format = new SimpleDateFormat("dd MMMM yyyy"); 
		Date date = new Date(2018-1900, 11, 31);  
		int julianDate = JulianDate.toJulianDate(date); 
		String julianText = String.format("%d", julianDate); 
		String msg = MessageFormat.format("{0} in Julian Date : {1}", format.format(date), julianText); 
		System.out.println(msg);	
		
		date = JulianDate.toDate(julianDate);
		msg = MessageFormat.format("Julian Date {0} is {1}", julianText, format.format(date)); 
		System.out.println(msg);
	}

}
