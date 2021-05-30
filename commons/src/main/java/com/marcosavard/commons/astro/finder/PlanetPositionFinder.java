package com.marcosavard.commons.astro.finder;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.marcosavard.commons.astro.AstroMath;
import com.marcosavard.commons.astro.JulianDay;
import com.marcosavard.commons.astro.SkyPosition;
import com.marcosavard.commons.astro.SpaceLocation;
import com.marcosavard.commons.astro.TimeConverter;
import com.marcosavard.commons.debug.Console;

import static com.marcosavard.commons.astro.AstroMath.sind;
import static com.marcosavard.commons.astro.AstroMath.cosd;
import static com.marcosavard.commons.astro.AstroMath.atan2d;
import static com.marcosavard.commons.astro.AstroMath.range;

//see http://cosinekitty.com/solar_system.html
public class PlanetPositionFinder {
	private static Map<Planet, PlanetPositionFinder> positionFinders = new HashMap<>(); 
	private Planet planet; 
	
	public static PlanetPositionFinder of(Planet planet) {
		PlanetPositionFinder positionFinder = positionFinders.get(planet); 
		
		if (positionFinder == null) {  
			positionFinder = new PlanetPositionFinder(planet); 
			positionFinders.put(planet, positionFinder); 
		}

		return positionFinder;
	}
	
	public PlanetPositionFinder(Planet planet) {
		this.planet = planet;
	}

	public SpaceLocation findHelioCentricLocation(ZonedDateTime moment) {
		//compute primary values
		double jd = JulianDay.of(moment).getValue() - JulianDay.JD2000; 
		double a = planet.getMeanDistance(jd);
		double ma = planet.getMeanAnomaly(jd); 
		double e = planet.getEccentricity(jd); 
		double i = planet.getInclination(jd);
		double n = planet.getLongitudeAscendingNode(jd);
		double w = planet.getArgumentOfPerihelion(jd); 
		
		Console.println("n={0}, i={1}, w={2} a={3} e={4} ma={5}", n, i, w, a, e, ma);
		
		//compute secondary
		double meanLongitude = AstroMath.range(w+ma, 0, 360); 
		double eccentricAnomaly = AstroMath.computeEccentricAnomaly(e, ma, 0.005); 
		
		//compute rectangular positions
		double x = a * (cosd(eccentricAnomaly) - e); 
		double y = a * Math.sqrt(1 - e * e) * sind(eccentricAnomaly);
		
		//compute spherical positions
	    double r = Math.sqrt(x*x + y*y); 
	    double v = range(atan2d(y, x), 0, 360); 
	    double lon = range(v+w, 0, 360);
	    
		//compute rectangular positions
	    double xeclip = r * ((cosd(n) * cosd(v+w)) - sind(n) * sind(v+w) * cosd(i));
	    double yeclip = r * ((sind(n) * cosd(v+w)) + cosd(n) * sind(v+w) * cosd(i)); 
	    double zeclip = r * (sind(v+w)) * sind(i); 
	    
	    //heliocentric location (ra=lon, dec=lat)
	    SpaceLocation helioCentricLocation = SpaceLocation.rectangleOf(xeclip, yeclip, zeclip); 
		return helioCentricLocation;
	}

	public SpaceLocation findGeoCentricLocation(ZonedDateTime moment) {
		SpaceLocation helioCentricLocation = findHelioCentricLocation(moment); 
		SpaceLocation sunLocation = SunPositionFinder.findLocation(moment); 
		sunLocation = SpaceLocation.rectangleOf(sunLocation.getX(), sunLocation.getY(), 0); 
		SpaceLocation equatorial = helioCentricLocation.addTo(sunLocation);
		SpaceLocation geoCentric = equatorial.rotateX(AstroMath.ECLIPTIC); 
		
		return geoCentric;
	}
	
	public SkyPosition findPosition(ZonedDateTime moment, double[] coordinates) {
		//compute momentUt 		
		ZonedDateTime momentUt = TimeConverter.toZonedDateTime(moment, ZoneOffset.UTC);
	
		//compute location
		SpaceLocation location = findGeoCentricLocation(moment); 
		
		//compute position
		SkyPosition position = SkyPositionFinder.findPosition(location, momentUt, coordinates); 
		return position;
	}
}
