package com.marcosavard.commons.astro.finder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.marcosavard.commons.astro.JulianDay;
import com.marcosavard.commons.astro.SkyPosition;
import com.marcosavard.commons.astro.SpaceLocation;
import com.marcosavard.commons.geog.GeoLocation;

import static com.marcosavard.commons.astro.AstroMath.cosd;
import static com.marcosavard.commons.astro.AstroMath.sind;
import static com.marcosavard.commons.astro.AstroMath.asind;
import static com.marcosavard.commons.astro.AstroMath.atan2d;

public class SunPositionFinderDemo {

	public static void main(String[] args) {
		findSunPositionAtAngers(); 
	}

	private static void findSunPositionAtAngers() {
		GeoLocation angers = GeoLocation.of(47.5, -0.6);
		LocalDate date = LocalDate.of(1981, 2, 5);
		LocalTime time = LocalTime.of(10, 15); 
		ZoneId paris = ZoneId.of("Europe/Paris");
		ZonedDateTime moment = ZonedDateTime.of(date, time, paris); 
				
		SpaceLocation sunLocation2 = SunPositionFinder.findLocation(moment);
		System.out.println("new method " + sunLocation2); 
		
		//gives azimuth=75.716° (E), horizon=328.85°
		SkyPosition sunPosition = SunPositionFinder.findPosition(moment, angers.toCoordinates());
		System.out.println(sunPosition); 		
		System.out.println(); 		
	}
	  
	  private static double range(double value, double range) {
			double ranged = value - Math.floor(value/range)*range;
			return ranged;
		}

}
