package com.marcosavard.commons.astro.eval;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.marcosavard.commons.geog.GeoLocation;

public class SunPosition2Demo {

	public static void main(String[] args) {
		GeoLocation angers = GeoLocation.of(47.5, -0.6);
		LocalDate date = LocalDate.of(1981, 2, 5);
		LocalTime time = LocalTime.of(10, 15); 
		ZoneId paris = ZoneId.of("Europe/Paris");
		ZonedDateTime moment = ZonedDateTime.of(date, time, paris); 
				
		SunPosition2 position = SunPosition2.of(moment, angers.toCoordinates());
		System.out.println(position); 

	}

}
