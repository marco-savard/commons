package com.marcosavard.commons.astro;


import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import com.marcosavard.commons.geog.GeoLocation;

public class AstronomyDemo {

  public static void main(String[] args) {
	  //findSunPositionAtAngers(); 
	  
	  //findSkyPositionOfAustin(); 
	  
    // gives asc=6.57 hr decl=41 degrees
    // GeoCoordinate madrid = GeoCoordinate.of(41, -3);
    // date = LocalDate.of(1983, 2, 1);
    // time = LocalTime.of(22, 0);
    // moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    // location = findSpaceLocation(SkyPosition.ZENITH, madrid, moment);
    // findSkyPosition(location, madrid, moment);

    //GeoLocation besancon = GeoLocation.of(47, 6);
   // SpaceLocation polaris = StarAlmanach.POLARIS;
   // date = LocalDate.of(1982, 1, 20);
  //  moment = Astronomy.findTimeAtMeridian(polaris, besancon, date);

  }


  

  
  private static void findSunPositionAtAngers() {
	  LocalDate date = LocalDate.of(1981, 2, 5);
      LocalTime time = LocalTime.of(10, 15); 
	  ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris")); 
		
	  GeoLocation angers = GeoLocation.of(47.5, -0.6);
	  double lat = angers.toCoordinates()[0]; 
	  double lon = angers.toCoordinates()[1];  
	  
	  //compute ecliptic coordinate (https://en.wikipedia.org/wiki/Position_of_the_Sun)
	  JulianDay jd =  JulianDay.of(moment);  
	  double n = jd.getValue() -2451545.0; 
	  double sunLon = range(280.460 + 0.9856474 * n, 360); 
	  double ma = range(357.528 + 0.9856003 * n, 360); 
	  
	  double eclLon = sunLon + (1.915 * sind(ma)) + (0.02 * sind(2 * ma)); 
	  double ecl = 23.439; 
	  double y = cosd(ecl) * sind(eclLon); 
	  double x = cosd(eclLon); 
	  double ra = atan2d(y, x); 
	  double dec = asind(sind(ecl) * sind(eclLon));
	  	  
	  SpaceLocation sunLocation =SpaceLocation.of(ra/15, dec); 
	  System.out.println("sunLocation(1) = " + sunLocation);  	
	  
	  SkyPosition position = findSkyPositionOfOld(sunLocation, moment, angers.toCoordinates()); 
	    
	  //should give azimuth=122° (SE), horizon=7°
	  System.out.println("position = " + position);  
  }
  


private static void findSunPositionAtAngers2() {
	  LocalDate date = LocalDate.of(1981, 2, 5);
      LocalTime time = LocalTime.of(10, 15); 
	  ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris")); 
		
	  GeoLocation angers = GeoLocation.of(47.5, -0.6);
	  double lat = angers.toCoordinates()[0]; 
	  double lon = angers.toCoordinates()[1];  
	  
	  //day of year
	  int dayOfYear = moment.toLocalDate().getDayOfYear(); 
	  
	  int n = (int)range(dayOfYear - 79, 366);
	  boolean springSummer = (n < 107); 
		
	  //expecting Declination:	-15.905°
	  //RightAscension:	21h 15m 37.53s
	  
		double dec, ra; 
		
		if (springSummer) { 
			dec = 23.44 * sind(n * 360.0 / 372.0);
			ra = 24 * (n / 372); 
		} else { 
			dec = 23.44 * sind(n * 360.0 / 359.0);
			ra = 12 + 24 * ((n-186) / 358.0); 
		}
		
	  SpaceLocation sunLocation =SpaceLocation.of(ra, dec); 
	  System.out.println("sunLocation(1) = " + sunLocation);  	
	  
	  double lngHour = lon / 15;
	  double hour = moment.getHour() + moment.getMinute() / 60.0;
	  double t = dayOfYear + ((hour - lngHour) / 24); 
	  
	  double ma = (0.9856 * t) - 3.289;
	  double sunLon  = ma + (1.916 * sind(ma)) + (0.020 * sind(2 * ma)) + 282.634;
			  
	  //compute right ascension, in degrees
	  double tanLon1 = tand(sunLon); 
	  ra = atand(0.91764 * tanLon1); 
		
	  double sinDec = 0.39782 * sind(sunLon);
	  dec = asind(sinDec); 	
	  
	  sunLocation =SpaceLocation.of(ra/15, dec); 
	  System.out.println("sunLocation(2) = " + sunLocation);  	
  }
  
  private static void findSunPositionAtAngersOld() {
	  LocalDate date = LocalDate.of(1981, 2, 5);
      LocalTime time = LocalTime.of(10, 15); 
	  ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris")); 
		
	  GeoLocation angers = GeoLocation.of(47.5, -0.6);
	  SpaceLocation sunLocation = findSunLocation(moment, angers.toCoordinates()); 	
	
	  SkyPosition position = findSkyPositionOfOld(sunLocation, moment, angers.toCoordinates()); 
	  System.out.println("position = " + position);  
	  
	  double lat = angers.toCoordinates()[0]; 
	  double lon = angers.toCoordinates()[1]; 
	  
	  JulianDay jd =  JulianDay.of(moment);  
	  double n = jd.getValue() -2451545.0; 
	  double mlon = range(280.460 + 0.9856474 * n, 360); 
	  double gmst0 = (mlon/15) + 12;  
	  
	  double sidtime = gmst0 + 9.25 + lon/15; 
	  double ra = sunLocation.getRightAscensionHour(); 
	  double decl = sunLocation.getDeclination();
	  double ha = sidtime - ra;
	 
	  double x = cosd(ha) * cosd(decl);
	  double y = sind(ha) * cosd(decl); 
	  double z = sind(decl);     
	  
	  double xhor = x * sind(lat) - z * cosd(lat); 
	  double yhor = y; 
	  double zhor = x * cosd(lat) + z * sind(lat);
			    
	  double azimuth  = atan2d( yhor, xhor ) + 180; 
      double altitude = asind( zhor );   
      
      position = SkyPosition.of(altitude, azimuth) ; 
			  
	  //should give azimuth=113° (SW), horizon=21°
      //az=134, hor=15 deg, according :
      //https://www.suncalc.org/#/47.4707,-0.5532,6/1981.02.05/10:30/1/3
	  System.out.println(position);  
}

  
  private static SpaceLocation findSunLocation(ZonedDateTime moment, double[] coordinates) {
	
	System.out.println("  moment : " + moment);  
	  
	//day of year
	int dayOfYear = moment.toLocalDate().getDayOfYear(); 
	
	//approx decl
	double decl = -23.44 * cosd((dayOfYear + 10) * 360 / 365); 
	//decl = range(decl, 360);
	System.out.println("  decl(1) : " + decl);  
	
	//ecLon (Earth's position in its orbit) (0 at vernal equinox)
	JulianDay jd =  JulianDay.of(moment);  
	System.out.println("  jd : " + jd);   
	double n = jd.getValue() -2451545.0; 
	double mlon = range(280.460 + 0.9856474 * n, 360); 
	double ma = range(357.528 + 0.9856003 * n, 360); 
	double ecLon = mlon + 1.915 * sind(ma) + 0.020 * sind(2 * ma); 
	System.out.println("  ecLon : " + ecLon);   
	
	//decl +23°26 summer Solstice, -23°26 winter solstice, 0 at equinox
	double sinDecl = sind(23.44) * sind(ecLon);
	//decl = asind(sinDecl); 
	System.out.println("  sind(23.44) : " + sind(23.44));  
	System.out.println("  decl(2) : " + decl);    
	
	sinDecl = 0.39782 * sind(ecLon); 
	//decl = asind(sinDecl);
	System.out.println("  decl(3) : " + decl);   
	
	double obEclip = 23.439 - (0.0000004 * n); 
	//double sinDecl = sind(obEclip) * sind(ecLon);
	
	
	
	double y = cosd(obEclip) * sind(ecLon); 
	double x = cosd(ecLon);
	double ra = atan2d(y, x); 
	System.out.println("  ra : " + ra);  
	System.out.println("  ra : " + ra/15);  
	
	//decl = asind(sinDecl); 
	System.out.println("  decl : " + decl);  
	
	
	double lat = coordinates[0];
	double lon = coordinates[1];
	

	SpaceLocation location = SpaceLocation.of(ra/15, decl); 
	return location;
  }


  
private static SpaceLocation findSunLocationOld(ZonedDateTime moment, double[] coordinates) {
	//day of year
	int dayOfYear = moment.toLocalDate().getDayOfYear(); 
	double lat = coordinates[0];
	double lon = coordinates[1];
			
	//convert the longitude to hour 
	double lngHour = lon / 15; 
			
    //convert approximate noon
	ZonedDateTime momentUt = toZonedDateTime(moment, ZoneOffset.UTC);
	double hourUt = momentUt.toLocalTime().toSecondOfDay() / 3600.0;  
	
	double tTimeApprox = dayOfYear + ((hourUt - lngHour) / 24); 
	tTimeApprox = range(tTimeApprox, 24);
	
	//convert meanAnomaly
	double m1 = (0.9856 * tTimeApprox) - 3.289; 
	
	//convert trueLongitude
	double lon1 = range(m1 + (1.916 * sind(m1)) + (0.020 * sind(2 * m1)) + 282.634, 360);
	
	//compute right ascension, in degrees
	double tanLon1 = tand(lon1); 
	double rightAscension = atan2d(0.91764, 1 / tanLon1); 
	
	//compute declination, in degrees
	double sinDec1 = 0.39782 * sind(lon1); 
	double cosLat = cosd(lat);
	double declination = asind(sinDec1);
	
	SpaceLocation location = SpaceLocation.of(rightAscension/15, declination); 
	return location;
}



private static SkyPosition findSkyPositionOfOld(SpaceLocation spaceLocation, ZonedDateTime moment, double[] coordinates) {
	//extract input values
	double ra = spaceLocation.getRightAscensionHour();
	double decl = spaceLocation.getDeclination(); 
	
	double lat = coordinates[0]; 
	double lon = coordinates[1]; 
	
	//day of year
	int n = moment.getDayOfYear(); 

	//convert to UT
	ZonedDateTime momentUt = toZonedDateTime(moment, ZoneOffset.UTC);
	double hr = momentUt.toLocalTime().toSecondOfDay() / 3600.0;  
	
	//compute tcl
	double tsl = computeTsl(n, hr, lon); 
	
	LocalSideralTime lst = LocalSideralTime.of(moment, lon);
	double lstv = range(lst.degrees(), 24);
    double ha = (lstv - ra) * 15;
	
	//compute ah 
	double ah = (tsl - ra) * 15; 
	
	//compute h 
	double sinH = (sind(lat) * sind(decl)) + (cosd(lat) * cosd(decl) * cosd(ah)); 
	double h = asind(sinH); 
	
	//compute az
	double y = sind(ah); 
	double cosAh = cosd(ah); 
	double sinLat = sind(lat); 
	double tanDecl = tand(decl); 
	double cosLat = cosd(lat); 
	
	double x = (cosAh * sinLat) - (tanDecl * cosLat); 
	double tanAz = y/x;
	double az = atand(tanAz);

	SkyPosition position = SkyPosition.of(h, az); 
	return position;
}

  
  private static double computeTsl(int dayOfYear, double hour, double lon) {
	double k = 6.617; //6.638322; //for 1981 
	double tsl = k + (0.065709 * dayOfYear) + (1.0022733 * hour) + (lon/15); 
	tsl = range(tsl, 24); 
	return tsl;
}



private static double range(double value, double range) {
	double ranged = value - Math.floor(value/range)*range;
	return ranged;
}

private static double tand(double degree) {
	return Math.tan(Math.toRadians(degree)); 
}

private static double asind(double a) { 
	return Math.toDegrees(Math.asin(a));
	
	//return range(Math.toDegrees(Math.asin(a)), 360);
}

private static double atand(double a) {
	return range(Math.toDegrees(Math.atan(a)), 360);
}

private static double atan2d(double y, double x) {
	return range(Math.toDegrees(Math.atan2(y, x)), 360);
}

private static double cosd(double degree) {
	return Math.cos(Math.toRadians(degree)); 
}

private static double sind(double degree) {
	return Math.sin(Math.toRadians(degree)); 
}

private static ZonedDateTime toZonedDateTime(ZonedDateTime moment, ZoneOffset utc) {
	Instant instant = moment.toInstant(); 
	ZonedDateTime ut = instant.atZone( utc );
	return ut;
}



private static void findSkyPositionOfAustin() {
	  // find equatorial coordinates, from sky position
	  LocalDate date = LocalDate.of(1984, 9, 5);
	  LocalTime time = LocalTime.of(21, 6);
	  ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris")); 
	  
	  // find location of austin at clairmontFerrand
	  SkyPosition austin = SkyPosition.of(14, 320);
	  GeoLocation clairmontFerrand = GeoLocation.of(46, 3);
	  SpaceLocation location = findSpaceLocation(austin, clairmontFerrand, moment);
	   
	  // gives asc=12.28 hr decl=43.658 degrees
	  findSkyPosition(location, clairmontFerrand, moment);
}



private static SpaceLocation findSpaceLocation(SkyPosition position, GeoLocation place,
      ZonedDateTime moment) {
    double[] coordinates = place.toCoordinates();
    SpaceLocation spaceLocation = Astronomy.findSpaceLocationOf(position, moment, coordinates);
    String msg = MessageFormat.format("Q: Which star is seen at {0} from {1} when {2}", position,
        place, moment);
    System.out.println(msg);
    System.out.println("A: " + spaceLocation);
    System.out.println();
    return spaceLocation;
  }

  private static SkyPosition findSkyPosition(SpaceLocation spaceLocation, GeoLocation place,
      ZonedDateTime moment) {
    double[] coordinates = place.toCoordinates();
    SkyPosition position = Astronomy.findSkyPositionOf(spaceLocation, moment, coordinates);
    String msg = MessageFormat.format("Q: Where to see star {0} from {1} where {2}", spaceLocation,
        place, moment);
    System.out.println(msg);
    System.out.println("A: " + position);
    System.out.println();
    return position;
  }

}
