package com.marcosavard.commons.astro.eval;

import java.text.MessageFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.marcosavard.commons.astro.LocalSideralTime;
import com.marcosavard.commons.astro.SkyPosition;
import com.marcosavard.commons.astro.SpaceLocation;
import com.marcosavard.commons.astro.TimeConverter;
import com.marcosavard.commons.math.Maths;

import static com.marcosavard.commons.astro.AstroMath.sind;
import static com.marcosavard.commons.astro.AstroMath.cosd;
import static com.marcosavard.commons.astro.AstroMath.range;
import static com.marcosavard.commons.astro.AstroMath.asind;
import static com.marcosavard.commons.astro.AstroMath.acosd;

public class SpaceLocationFinder {

	//// Ref: http://www-star.st-and.ac.uk/~fv/webnotes/index.html
	public static SpaceLocation find(SkyPosition position, double[] coordinates, ZonedDateTime moment) {
		//read data
		double lat = coordinates[0];
	    double lon = coordinates[1];
	    double h = position.getHorizon();
	    double az = position.getAzimuth();
	    
	    //compute lst
	    LocalSideralTime lst = LocalSideralTime.of(moment, lon);
	    System.out.println("  lst = " + lst); 
	    
	    int n = moment.getDayOfYear(); 
	    ZonedDateTime momentUt = TimeConverter.toZonedDateTime(moment, ZoneOffset.UTC);
	    double hr = momentUt.toLocalTime().toSecondOfDay() / 3600.0;  
	    double tsl = computeTsl(n, hr, lon); 
		System.out.println("  tsl = " + tsl);
	    
	    //compute dec 
	    double a1 = sind(h) * sind(lat); 
	    double a2 = cosd(h) * cosd(lat) * cosd(az); 
	    double dec = asind(a1 + a2); 

	    //compute ra 
	    double b1 = sind(h) - sind(lat) * sind(dec); 
	    double b2 = cosd(lat) * cosd(dec); 
	    double b = b1 / b2;
        b = Maths.equal(b, 1, 0.01) ? 1.0 : b; 
        double c = acosd(b); 
        double ra = lst.hours() - (c / 15);
	    
        SpaceLocation location = SpaceLocation.of(ra, dec); 
		return location;
	}
	
	private static double computeTsl(int dayOfYear, double hour, double lon) {
		double k = 6.617; // 6.617; //6.638322; //for 1981 
		double n = (0.065709 * dayOfYear);
		double h = (1.0022733 * hour); 
		double l = (lon/15); 
		double tsl = k + n + h + l; 
		
		String msg = MessageFormat.format("tsl = {0} + {1} + {2} + {3}", //
			k, n, h, l); 
		System.out.println(msg); 
		
		System.out.println("  tsl = " + tsl);
		tsl = range(tsl, 24);  
		
		System.out.println("  tsl = " + tsl);
		
		return tsl;
    }

}
