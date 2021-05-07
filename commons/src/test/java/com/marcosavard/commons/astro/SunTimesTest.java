package com.marcosavard.commons.astro;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.Test;

import com.marcosavard.commons.astro.eval.SunTimes;

import junit.framework.Assert;

public class SunTimesTest {
	
	@Test
	public void testWayneNJ() {
		ZoneId est = ZoneId.of("America/New_York");
		double[] wayneNJ = Location.of(40.9, -74.3);

		SunTimes times = test(LocalDate.of(1990, 6, 25), wayneNJ, est, SunTimes.OFFICIAL_HORIZON);
		LocalTime sunRise = times.getSunRise(); 
		LocalTime expected = LocalTime.of(5, 26); //should give {sunrise = 5:26 am EDT}
		assertEqual(expected, sunRise); 
	}
	
	@Test
	public void testQuebec() {
		ZoneId est = ZoneId.of("America/New_York");
		double[] qc = Location.of(46.8139, -71.2080);

		SunTimes times = test(LocalDate.of(2021, 4, 21), qc, est, SunTimes.OFFICIAL_HORIZON);
		LocalTime sunRise = times.getSunRise(); 
		LocalTime expected = LocalTime.of(5, 46); //should give {sunrise = 5:46 am EDT}
		assertEqual(expected, sunRise); 
	}
	
	private void assertEqual(LocalTime expectedTime, LocalTime actualTime) {
		int expected = expectedTime.toSecondOfDay(); 
		int actual = actualTime.toSecondOfDay(); 
		Assert.assertEquals(expected, actual); 
	}

	private SunTimes test(LocalDate date, double[] location, ZoneId zone, double angle) {
		SunTimes times = SunTimes.of(date, location, zone, angle);
		return times;
	}

	private static class Location {
		public static double[] of(double lat, double lon) {
			double[] coordinates = new double[] {lat, lon};
			return coordinates;
		}
	}

}
