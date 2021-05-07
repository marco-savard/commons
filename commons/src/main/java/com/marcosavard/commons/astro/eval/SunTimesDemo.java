package com.marcosavard.commons.astro.eval;

import java.time.LocalDate;
import java.time.ZoneId;

public class SunTimesDemo {

	//check result : https://www.calculatorsoup.com/calculators/time/sunrise_sunset.php
	//https://www.timeanddate.com/astronomy/night/usa/new-york
	public static void main(String[] args) {
		demoSunTimesAtWayneNj();
		demoSunTimesAtQuebec();
		demoSunTimesAtNyc();
		demoSunTimesAtMurmansk();
	}
	
	private static void demoSunTimesAtWayneNj() {
		//Wayne, NJ  40.9, -74.3, should give {sunrise = 5:26 am EDT}
		double[] wayneNJ = Location.of(40.9, -74.3);
		ZoneId est = ZoneId.of("America/New_York");
		test(LocalDate.of(1990, 6, 25), wayneNJ, est, SunTimes.OFFICIAL_HORIZON);
	}
	
	private static void demoSunTimesAtQuebec() {
		double[] qc = Location.of(46.8139, -71.2080);
		ZoneId est = ZoneId.of("America/New_York");
		test(LocalDate.of(2021, 4, 21), qc, est, SunTimes.OFFICIAL_HORIZON);
	}
	
	private static void demoSunTimesAtNyc() {
		double[] nyc = Location.of(40.7128, -74.0060);
		ZoneId est = ZoneId.of("America/New_York");
		test(LocalDate.of(2021, 4, 21), nyc, est, SunTimes.OFFICIAL_HORIZON);
	}
	
	private static void demoSunTimesAtMurmansk() {
		double[] murmansk = Location.of(68.9733, 33.0856);
		ZoneId moscow = ZoneId.of("Europe/Moscow");
		test(LocalDate.of(2021, 6, 21), murmansk, moscow, SunTimes.OFFICIAL_HORIZON);
		test(LocalDate.of(2021, 12, 21), murmansk, moscow, SunTimes.OFFICIAL_HORIZON);
	}

	private static void test(LocalDate date, double[] location, ZoneId zone, double angle) {
		SunTimes times = SunTimes.of(date, location, zone, angle);
		System.out.println(times); 
	}

	private static class Location {
		public static double[] of(double lat, double lon) {
			double[] coordinates = new double[] {lat, lon};
			return coordinates;
		}
	}

}
