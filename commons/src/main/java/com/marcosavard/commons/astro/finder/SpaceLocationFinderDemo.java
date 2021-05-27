package com.marcosavard.commons.astro.finder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.marcosavard.commons.astro.SkyPosition;
import com.marcosavard.commons.astro.SpaceLocation;
import com.marcosavard.commons.astro.StarAlmanach;
import com.marcosavard.commons.geog.GeoLocation;

public class SpaceLocationFinderDemo {

	public static void main(String[] args) {
		findTimeAtMeridian(); 
		//findStarAtZenithAboveMadrid();  
	    //findStarAtAjaccio();
	    //findStarInCentralScandinavia();
	}

	private static void findTimeAtMeridian() {
	    GeoLocation besancon = GeoLocation.of(47, 6);
		SpaceLocation polaris = StarAlmanach.POLARIS;
		LocalDate date = LocalDate.of(1982, 1, 20);
		ZonedDateTime moment = SpaceLocationFinder.findTimeAtMeridian(polaris, besancon.toCoordinates(), date);  
		System.out.println(moment);
	}

	private static void findStarInCentralScandinavia() {
		// 19 april 1990, at midnight UTC
		LocalDate date = LocalDate.of(1990, 4, 19); //19 april
		LocalTime time = LocalTime.MIDNIGHT; 
		ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC); 
		
		//in central scandinavia 
	    GeoLocation scandinavia = GeoLocation.of(60, 15); 

	    double horizon = -17.96; 
	    double azimuth = 15.68; 
	    SkyPosition position = SkyPosition.of(horizon, azimuth); 
	    
	    //should give ra=1.77720, dec=11.0084 
	    SpaceLocation location = SpaceLocationFinder.find(position, scandinavia.toCoordinates(), moment);
	    System.out.println(location);
	    
	
	    
	    

	}

	private static void findStarAtAjaccio() {
		// 31 july 1981, at 21:30 
		LocalDate date = LocalDate.of(1981, 7, 31); //31 july
		LocalTime time = LocalTime.of(21, 30); //21:30 
		ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris")); 
				
	    //at ajaccio 
	    GeoLocation ajaccio = GeoLocation.of(42, 9); 
	    
	    double horizon = 6.2; 
	    double azimuth = 278; 
	    SkyPosition position = SkyPosition.of(horizon, azimuth); 
	    
	    //should give 10.667, 10 
	    SpaceLocation location = SpaceLocationFinder.find(position, ajaccio.toCoordinates(), moment);
	    System.out.println(location);

		
	}

	private static void findStarAtZenithAboveMadrid() {
	  
   
		// which star is at zenith above madrid on 1st feb 1983, at 22:00
	    GeoLocation madrid = GeoLocation.of(41, -3);
	    LocalDate date = LocalDate.of(1983, 2, 1);
	    LocalTime time = LocalTime.of(22, 0);
	    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
	    
	    // should give asc=6.57 hr decl=41 degrees
	    SpaceLocation location = SpaceLocationFinder.find(SkyPosition.ZENITH, madrid.toCoordinates(), moment);
	    System.out.println(location);
	    
	    //check result 
	    SkyPosition position2 = SkyPositionFinder.findPosition(location, moment, madrid.toCoordinates()); 
	    System.out.println(position2);
	    
	    //compare error 
	    double dist = position2.distanceFrom(SkyPosition.ZENITH); 
	    System.out.println("angular distance = " + dist);
	}

}
