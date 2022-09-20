package com.marcosavard.commons.astro.finder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.marcosavard.commons.astro.SkyPosition;
import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.geog.GeoLocation;

public class PlanetPositionFinderDemo {

	public static void main(String[] args) {
		LocalDate date = LocalDate.of(1990, 4, 19);
		LocalTime time = LocalTime.MIDNIGHT; 
		ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
		PlanetPositionFinder positionFinder = PlanetPositionFinder.of(Planet.MERCURY); 
		SpaceCoordinate mercuryLocation = positionFinder.findHelioCentricLocation(moment);
		System.out.println(mercuryLocation); 

		/*
		mercuryLocation = positionFinder.findGeoCentricLocation(moment);
		System.out.println(mercuryLocation); 
		
		GeoLocation centralScandinavia = GeoLocation.of(60, 15);
		SkyPosition position = positionFinder.findPosition(moment, centralScandinavia.toCoordinates());
		System.out.println(position); */
	}

}
