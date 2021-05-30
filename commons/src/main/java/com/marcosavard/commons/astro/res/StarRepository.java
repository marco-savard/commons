package com.marcosavard.commons.astro.res;

import java.util.List;

import com.marcosavard.commons.astro.SpaceLocation;

public class StarRepository {
	private static List<Star> allStars = null; 
	
	public static List<Star> getStars() {
		if (allStars == null) { 
			// get data
			StarFile starFile = StarFile.ofType(Star.class); 
			allStars = starFile.readAll(); 
		}
		
		return allStars; 
	}
	
	public static Star findStarNearestFromLocation(SpaceLocation location) {
		double ra = location.getRightAscensionHour();
		double dec = location.getDeclination();
		return findStarNearestFromLocation(ra, dec);
	}
	
	public static Star findStarNearestFromLocation(double ra, double dec) {
		List<Star> stars = getStars();
		Star nearestStar = stars.get(0); 
		double neareastDistance2 = Double.MAX_VALUE; 
		
		for (Star star : stars) { 
			double distance2 = star.findDistance2From(ra, dec); 
			
			if (distance2 < neareastDistance2) { 
				neareastDistance2 = distance2; 
				nearestStar = star; 
			}
		}
		
		return nearestStar;
	}



}
