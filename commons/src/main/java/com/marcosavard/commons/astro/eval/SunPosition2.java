package com.marcosavard.commons.astro.eval;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.marcosavard.commons.astro.AstroMath.range;
import static com.marcosavard.commons.astro.AstroMath.sind;
import static com.marcosavard.commons.astro.AstroMath.cosd;
import static com.marcosavard.commons.astro.AstroMath.asind;
import static com.marcosavard.commons.astro.AstroMath.acosd;

public class SunPosition2 {

	public static SunPosition2 of(ZonedDateTime moment, double[] coordinates) {
		SunPosition2 position = new SunPosition2(moment, coordinates); 
		return position;
	}
	
	public SunPosition2(ZonedDateTime moment, double[] coordinates) {
		//day of year
		LocalDate date = moment.toLocalDate();
		int dayOfYear = date.getDayOfYear(); 
		double lat = coordinates[0];
	    double lon = coordinates[1];
		
		//convert to UT
		ZonedDateTime momentUt = toZonedDateTime(moment, ZoneOffset.UTC);
		double hourUT = momentUt.toLocalTime().toSecondOfDay() / 3600.0;  
		System.out.println("hr : " + hourUT); 
		
		int n = (int)range(dayOfYear - 79, 366);
		boolean springSummer = (n < 107); 
		
		double decl, ra; 
		
		if (springSummer) { 
			decl = 23.44 * sind(n * 360.0 / 372.0);
			ra = 24 * (n / 372); 
		} else { 
			decl = 23.44 * sind(n * 360.0 / 359.0);
			ra = 12 + 24 * ((n-186) / 358.0); 
		}
		
		//compute ah
		double k = 6.6383;
		double tsl = k + (0.0657 * n) + (1.002734 * hourUT) + (lon / 15);
		double ah = tsl - ra;

		//compute h 
		double sinH = (sind(lat) * sind(decl)) + (cosd(lat) * cosd(decl) * cosd(ah * 15)); 
		double h = asind(sinH); 
		
		//compute az
		double cosAz = (sind(decl) - sind(lat) * sinH) / (cosd(lat) * cosd(h));
		double az = acosd(cosAz); 
		
		System.out.println("h : " + h); 
		System.out.println("az : " + az); 
		
	}
	
	private static ZonedDateTime toZonedDateTime(ZonedDateTime moment, ZoneOffset utc) {
		Instant instant = moment.toInstant(); 
		ZonedDateTime ut = instant.atZone( utc );
		return ut;
	}

}
