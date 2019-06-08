package com.marcosavard.commons.astro;

import java.util.Date;

public class LocalSideralTime {

	public static double getLst(Date moment, double longitude) {
		//computation
		double jd = dateToJulian(moment); 
		double ut = toDecimalHours(moment);
		double d = (jd-2451545.0); 
		//double t = d/36525.0; //century since 2000
		//double t0 = 6.697374558 + (2400.051336*t) + (0.000025862*t*t) + (ut*1.0027379093); 
		double lst = 100.46 + 0.985647 * d + longitude + 15*ut;
		lst = (lst > 0) ? (lst % 360) : (360 - Math.abs(lst) % 360) % 360; //keep in range [0..360]		
		return lst;
	}
	
	public static double dateToJulian(Date date) {
	    int year = date.getYear() + 1900;
	    int month = date.getMonth() + 1;
	    int day = date.getDate(); 
	    int hour = date.getHours(); 
	    int minute = date.getMinutes();
	    int second = date.getSeconds();

	    double extra = (100.0 * year) + month - 190002.5;
	    double julian =  (367.0 * year) -
	      (Math.floor(7.0 * (year + Math.floor((month + 9.0) / 12.0)) / 4.0)) + 
	      Math.floor((275.0 * month) / 9.0) +  
	      day + ((hour + ((minute + (second / 60.0)) / 60.0)) / 24.0) +
	      1721013.5 - ((0.5 * extra) / Math.abs(extra)) + 0.5;
	    return julian;
	  }

	private static double toDecimalHours(Date datetime) {
		double decimalHours = datetime.getHours();
		decimalHours += datetime.getMinutes() / 60.0;
		decimalHours += datetime.getSeconds() / 3600.0;
		return decimalHours;
	}
}
