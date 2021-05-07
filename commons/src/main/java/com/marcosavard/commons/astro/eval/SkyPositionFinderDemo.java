package com.marcosavard.commons.astro.eval;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Assert;

import com.marcosavard.commons.astro.Astronomy;
import com.marcosavard.commons.astro.SkyPosition;
import com.marcosavard.commons.astro.SpaceLocation;
import com.marcosavard.commons.astro.StarAlmanach;
import com.marcosavard.commons.geog.GeoLocation;

public class SkyPositionFinderDemo {

	public static void main(String[] args) {
		findVenusPositionAtAjaccio(); 
		findMarsPositionAtAvignon();
		findSunPositionInCentralScandinavia();
		findM13PositionInBirmingham();
		findStarsInQuebecCity();
	}
	
	private static void findVenusPositionAtAjaccio() {
		// 31 july 1981, at 21:30 
		LocalDate date = LocalDate.of(1981, 7, 31); //31 july
	    LocalTime time = LocalTime.of(21, 30); //21:30 
	    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris")); 
		
	    //at ajaccio 
	    GeoLocation ajaccio = GeoLocation.of(42, 9); 
	    
	    SpaceLocation venusLocation = SpaceLocation.of(10.667, 10); 
	    SkyPosition position = SkyPositionFinder.findPosition(venusLocation, moment, ajaccio.toCoordinates()); 
	    
	    //should give azimuth=278° (NW), horizon=6.2°
	    //according http://www.convertalot.com/celestial_horizon_co-ordinates_calculator.html
	    System.out.println(position);  
	}
	
	private static void findMarsPositionAtAvignon() {
		// 31 july 1981, at 21:30 
		LocalDate date = LocalDate.of(1981, 7, 1); //1 july
		LocalTime time = LocalTime.of(5, 0); //5 am 
		ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris")); 
		
		//at avignon 
	    GeoLocation avignon = GeoLocation.of(44, 5); 
	    
	    SpaceLocation marsLocation = SpaceLocation.of(5.138, 23.2); 
	    SkyPosition position = SkyPositionFinder.findPosition(marsLocation, moment, avignon.toCoordinates()); 
	    
	    //should give azimuth=61.2° (NE), horizon=4.0°
	    System.out.println(position);  
	}
	
	private static void findSunPositionInCentralScandinavia() {
		// 19 april 1990, at midnight UTC
		LocalDate date = LocalDate.of(1990, 4, 19); //19 april
		LocalTime time = LocalTime.MIDNIGHT; 
		ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC); 
		
		//in central scandinavia 
	    GeoLocation scandinavia = GeoLocation.of(60, 15); 
				
	    SpaceLocation sunLocation = SpaceLocation.of(1.77720, 11.0084); 
	    SkyPosition position = SkyPositionFinder.findPosition(sunLocation, moment, scandinavia.toCoordinates()); 
	    
	    //should give azimuth=15.68° (N), horizon=-17.96°
	    System.out.println(position);  
	}
	
	// http://www.stargazing.net/kepler/altaz.html
	private static void findM13PositionInBirmingham() {
		// position of the star M13..
	    SpaceLocation m13 = StarAlmanach.M13;

	    // ..as seen from this location
	    GeoLocation birminghamUK = GeoLocation.of(52, 30, NORTH, 1, 55, WEST);
		
	    // ..at this moment
	    LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
	    ZonedDateTime moment = ZonedDateTime.of(localTime, ZoneOffset.UTC);
	    
	    SkyPosition position = SkyPositionFinder.findPosition(m13, moment, birminghamUK.toCoordinates()); 
	    	    
	    System.out.println("Position of star M13 above Birmingham, UK on August 10st, 1998 at 23:10");
	    System.out.println("  ..position of M13: " + position);
	    System.out.println();
	    
	    Assert.assertEquals(position.getAzimuth(), 269.1, 0.5);
	    Assert.assertEquals(position.getHorizon(), 49.2, 0.5);
	}
	
	private static void findStarsInQuebecCity() {
		    System.out.println("Position of some stars above Quebec City on January 1st, 2019 at 6PM");

		    // as seen at this location
		    GeoLocation qcCity = GeoLocation.of(46, 49, NORTH, 71, 13, WEST);

		    // at this moment
		    LocalDateTime localTime = LocalDateTime.of(2019, Month.JANUARY, 1, 18, 0, 0); // Jan1st, 6PM
		    ZonedDateTime moment = localTime.atZone(ZoneId.of("America/New_York"));

		    // get sky position
		    SkyPosition position;
		    position = SkyPositionFinder.findPosition(StarAlmanach.POLARIS, moment, qcCity.toCoordinates()); 
		    System.out.println("  ..position of Polaris: " + position);
		    
		    position = SkyPositionFinder.findPosition(StarAlmanach.URSA_MAJOR_EPSILON, moment, qcCity.toCoordinates()); 
		    System.out.println("  ..position of Ursa Major Epsilon: " + position);
		    
		    position = SkyPositionFinder.findPosition(StarAlmanach.SIRIUS, moment, qcCity.toCoordinates()); 
		    System.out.println("  ..position of Sirius: " + position);
		    
		    position = SkyPositionFinder.findPosition(StarAlmanach.ANTARES, moment, qcCity.toCoordinates()); 
		    System.out.println("  ..position of Antares: " + position);
		    
		    position = SkyPositionFinder.findPosition(StarAlmanach.CRUX_ALPHA, moment, qcCity.toCoordinates()); 
		    System.out.println("  ..position of Crux Alpha: " + position);
		

		    System.out.println();
		  }



}
