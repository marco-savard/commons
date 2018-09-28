package com.marcosavard.commons.time;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Date;

public class DatesDemo {

	public static void main(String[] args) {
		Date now = new Date();
		
		Date today = new Date(now.getYear(), now.getMonth(), now.getDate()); 
		LocalDate ld = Dates.toLocalDate(today); 
		Date d2 = Dates.toDate(ld); 
		
		String msg = MessageFormat.format("d1:{0} -> ld:{1} -> d2:{2}", today, ld, d2); 
		System.out.println(msg); 
		System.out.println(" d1 equal to d2 : " + today.equals(d2)); 

	}

}
