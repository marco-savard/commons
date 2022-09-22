package com.marcosavard.commons.astro.finder;

import com.marcosavard.commons.astro.space.SpaceCoordinate;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.marcosavard.commons.astro.AstroMath.range;

public class SpaceLocationFinder {

	/*

	//// Ref: http://www-star.st-and.ac.uk/~fv/webnotes/index.html
	public static SpaceCoordinate find(SkyPosition position, double[] coordinates, ZonedDateTime moment) {
		//read data
		double lat = coordinates[0];
	    double lon = coordinates[1];
	    double h = position.getHorizon();
	    double az = position.getAzimuth();
	    
	    //compute lst
	    LocalSideralTime lst = LocalSideralTime.of(moment, lon);
	    
	    int n = moment.getDayOfYear(); 
	    ZonedDateTime momentUt = TimeConverter.toZonedDateTime(moment, ZoneOffset.UTC);
	    double hr = momentUt.toLocalTime().toSecondOfDay() / 3600.0;  
	    double tsl = computeTsl(n, hr, lon); 
		//System.out.println("  tsl = " + tsl);
	    
	    //compute dec 
	    double a1 = sind(h) * sind(lat); 
	    double a2 = cosd(h) * cosd(lat) * cosd(az); 
	    double dec = asind(a1 + a2); 

	    //compute ra         
        double a = (sind(h) - sind(lat) * sind(dec)) / (cosd(lat) * cosd(dec));
        a = Math.min(1.0, a); //correct precision error
        double acosA = acosd(a); 
        double ra = lst.hours() - (acosA / 15); 
        	    
        SpaceCoordinate location = SpaceCoordinate.of(ra, dec);
		return location;
	}
	*/
	private static double computeTsl(int dayOfYear, double hour, double lon) {
		double k = 6.617; // 6.617; //6.638322; //for 1981 
		double n = (0.065709 * dayOfYear);
		double h = (1.0022733 * hour); 
		double l = (lon/15); 
		double tsl = k + n + h + l; 
		
		String msg = MessageFormat.format("tsl = {0} + {1} + {2} + {3}", //
			k, n, h, l); 
		
		tsl = range(tsl, 24);  
		
		return tsl;
    }

	public static ZonedDateTime findTimeAtMeridian(SpaceCoordinate spaceLocation, double[] coordinates, LocalDate date) {
		for (int h = 0; h < 24; h++) {
		      for (int m = 0; m < 6; m++) {
		        LocalTime time = LocalTime.of(h, m * 10);
		        ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
		        //SkyPosition position = findSkyPositionOf(spaceLocation, moment, geoLocation);
		        //System.out.println(MessageFormat.format("  {0} {1}", moment, position));
		      }

		    }

		return null;
	}

}
