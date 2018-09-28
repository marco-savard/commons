package com.marcosavard.commons.time;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.marcosavard.commons.time.HolidayCalendar.Holiday;

public class HolidayCalendarDemo {
	
	public static void main(String[] args) {
		printHolidaysOf(2018); 
		
		printNextHolidays(); 
	}

	private static void printHolidaysOf(int year) {
		System.out.println(MessageFormat.format("Holidays for year {0} :", String.format("%d", year)));
		
		Holiday[] holidays = Holiday.values(); 
		for (Holiday holiday : holidays) {
			Date date = HolidayCalendar.findHolidayOfYear(holiday, year); 
			String s = new SimpleDateFormat("MMMM d").format(date); 
			System.out.println(MessageFormat.format("  {0} : {1}", holiday, s)); 
		}
		
		System.out.println();
	}

	private static void printNextHolidays() {
		System.out.println("Next holidays :"); 
		
		Holiday[] holidays = Holiday.values(); 
		List<Date> nextHolidays = new ArrayList<>(); 
		Map<Date, Holiday> nextHolidaysByDate = new HashMap<>(); 
		
		for (HolidayCalendar.Holiday holiday : holidays) {
			Date date = HolidayCalendar.findNextHoliday(holiday); 
			nextHolidays.add(date); 
			nextHolidaysByDate.put(date, holiday);
		}
		
		Collections.sort(nextHolidays);
		Date today = new Date(); 

		for (Date date : nextHolidays) {
			Holiday holiday = nextHolidaysByDate.get(date); 
			String s = new SimpleDateFormat("MMMM d yyyy").format(date); 
			long daysRemaining = Dates.daysBetween(today, date);
			System.out.println(MessageFormat.format("  {0} : {1} (in {2} days)", s, holiday, daysRemaining)); 
		}
		
		System.out.println();
		
	}
}
