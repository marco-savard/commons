package com.marcosavard.commons.astro.finder;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.marcosavard.commons.astro.SkyPosition;
import com.marcosavard.commons.astro.SpaceLocation;
import com.marcosavard.commons.astro.TimeConverter;

import static com.marcosavard.commons.astro.AstroMath.range;
import static com.marcosavard.commons.astro.AstroMath.sind;
import static com.marcosavard.commons.astro.AstroMath.cosd;
import static com.marcosavard.commons.astro.AstroMath.tand;
import static com.marcosavard.commons.astro.AstroMath.asind;
import static com.marcosavard.commons.astro.AstroMath.atand;
import static com.marcosavard.commons.astro.AstroMath.atan2d;

public class SkyPositionFinder {

	//find h, az from ra, dec at location on moment 	
	public static SkyPosition findPosition(SpaceLocation location, ZonedDateTime moment, double[] coordinates) {
		//extract input values
		double ra = location.getRightAscensionHour();
		double dec = location.getDeclination(); 
		
		double lat = coordinates[0]; 
		double lon = coordinates[1]; 
		
		//day of year
		int n = moment.getDayOfYear(); 

		//convert to UT
		ZonedDateTime momentUt = TimeConverter.toZonedDateTime(moment, ZoneOffset.UTC);
		double hr = momentUt.toLocalTime().toSecondOfDay() / 3600.0;  
		
		//compute tsl
		double tsl = computeTsl(n, hr, lon); 
		//System.out.println("  tsl = " + tsl);
		
		//compute ah 
		double ha = (tsl - ra) * 15; 
		//System.out.println("  ha = " + ha);
		
		SkyPosition position = findPosition(ha, dec, lat); 
		//System.out.println("  position(2) = " + position);
		
		return position;
	}
	
	//find h, az (from https://stjarnhimlen.se/comp/tutorial.html#6)		
	private static SkyPosition findPosition(double ha, double dec, double lat) {

		//compute x,y,z
		double x = cosd(ha) * cosd(dec);
		double y = sind(ha) * cosd(dec); 
		double z = sind(dec);
		
		double xhor = x * cosd(90 - lat) - z * sind(90 - lat);
		double yhor = y; 
		double zhor = x * cosd(lat) + z * sind(lat);
		 
		//compute h, in range -180..180
		double h = range(asind(zhor), -180, 180);
		
		//compute az
		double az  = atan2d( yhor, xhor ) + 180;
		SkyPosition position = SkyPosition.of(h, az); 
		//System.out.println("  position(2) = " + position);
		
		return position;
	}
	
	//from science et vie, less accurate
	public static SkyPosition findPositionOld(double ha, double dec, double lat) {
		
		//compute h 
		double sinH = (sind(lat) * sind(dec)) + (cosd(lat) * cosd(dec) * cosd(ha)); 
		double h = asind(sinH); 
		System.out.println("  h = " + h);
		
		//compute az
		double y = sind(ha); 
		System.out.println("  y = " + y);
		
		double cosAh = cosd(ha); 
		double sinLat = sind(lat); 
		double tanDec = tand(dec); 
		double cosDec = cosd(dec); 
		
		double x = (cosAh * sinLat) - (tanDec * cosDec); 
		String msg = MessageFormat.format("x = ({0} * {1}) - ({2} * {3})", //
				cosAh, sinLat, tanDec, cosDec); 
		System.out.println(msg); 
		
		double tanAz = y/x;
		System.out.println("  tanAz = " + tanAz);
		
		double az = atand(tanAz);
		System.out.println("  az = " + az);
		
		SkyPosition position = SkyPosition.of(h, az); 
		System.out.println("  position(1) = " + position);
				
		return position;
	}
	
	private static double computeTsl(int dayOfYear, double hour, double lon) {
		double k = 6.617; // 6.617; //6.638322; //for 1981 
		double n = (0.065709 * dayOfYear);
		double h = (1.0022733 * hour); 
		double l = (lon/15); 
		double tsl = k + n + h + l; 
		
		String msg = MessageFormat.format("tsl = {0} + {1} + {2} + {3}", //
			k, n, h, l); 
		//System.out.println(msg); 
		
		//System.out.println("  tsl = " + tsl);
		tsl = range(tsl, 24);  
		
		//System.out.println("  tsl = " + tsl);
		
		return tsl;
    }

}
