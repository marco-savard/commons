package com.marcosavard.commons.astro.finder;

public class SunPositionFinderDemo {

	public static void main(String[] args) {
		//findSunPositionInScandinavia();
		//findSunPositionOnSpringEquinox();
		//findSunPositionAtAngers();
		//findSunPositionInQuebec(); 
	}

	/*
	private static void findSunPositionInScandinavia() {
		LocalDate date = LocalDate.of(1990, 4, 19);
		LocalTime time = LocalTime.MIDNIGHT; 
		ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
		SpaceCoordinate sunLocation = SunPositionFinder.findLocation(moment);
		
		GeoLocation centralScandinavia = GeoLocation.of(60, 15);
		SkyPosition position = SkyPositionFinder.findPosition(sunLocation, moment, centralScandinavia.toCoordinates()); 
		
		Console.println("On {0}", moment);
		Console.println("  ..Sun is located at {0}", sunLocation);
		Console.println(); 
		
		Console.println("At {0}", centralScandinavia);
		Console.println("  ..Sun can be seen at {0}", position);
		Console.println(); 
	}
*/

	/*
	private static void findSunPositionInQuebec() {
		LocalDate date = LocalDate.of(2021, 5, 28);
		LocalTime time = LocalTime.NOON; 
		ZonedDateTime moment = ZonedDateTime.of(date, time,  ZoneId.of("America/Montreal")); 
		SpaceCoordinate sunLocation = SunPositionFinder.findLocation(moment);
		Console.println(sunLocation);
	}*/

	/*
	private static void findSunPositionOnSpringEquinox() {
		LocalDate date = LocalDate.of(2021, 3, 20); //20th mars
		LocalTime time = LocalTime.NOON; 
		ZonedDateTime springEquinox = ZonedDateTime.of(date, time, ZoneOffset.UTC); 
		SpaceCoordinate sunLocation = SunPositionFinder.findLocation(springEquinox);
		Console.println("On spring equinox, Sun is located at : " + sunLocation);  
		
		double dist = sunLocation.distanceFrom(SpaceCoordinate.VERNAL_POINT);
		Console.println("  Distance from vernal point (deg) : " + dist);   
		
	    SkyPosition position = SkyPositionFinder.findPosition(sunLocation, springEquinox, GeoLocation.NULL_ISLAND.toCoordinates()); 
	    Console.println("  At Null Island, Sun can be seen at : " + position);   
	    
	    dist = position.distanceFrom(SkyPosition.ZENITH); 
	    Console.println("  Distance from Zenith(degrees) : " + dist);   
	    Console.println();
	}*/

	//According: 
	//https://www.suncalc.org/#/47.4707,-0.5532,12/1981.02.05/10:15/1/3
	//ra=21h 15m 47s, dec=-15.89
	//h=14.86, az=134.99
	/*
	private static void findSunPositionAtAngers() {
		GeoLocation angers = GeoLocation.of(47.5, -0.6);
		LocalDate date = LocalDate.of(1981, 2, 5);
		LocalTime time = LocalTime.of(10, 15); 
		ZoneId paris = ZoneId.of("Europe/Paris");
		ZonedDateTime moment = ZonedDateTime.of(date, time, paris); 
				
		SpaceCoordinate sunLocation = SunPositionFinder.findLocation(moment);
		Console.println("On {0}, Sun is located at {1}", moment, sunLocation); 
		
		SkyPosition sunPosition = SunPositionFinder.findPosition(moment, angers.toCoordinates());
		Console.println("  In Angers, Sun can be seen at " + sunPosition); 		
		Console.println(); 		
	}
	  */
	  private static double range(double value, double range) {
			double ranged = value - Math.floor(value/range)*range;
			return ranged;
		}

}
