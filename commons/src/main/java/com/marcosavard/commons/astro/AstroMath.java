package com.marcosavard.commons.astro;

public class AstroMath {
	
	public static double range(double value, double range) {
		double ranged = value - Math.floor(value/range)*range;
		return ranged;
	}
	
	// keep in range [min..max]
	public static double range(double value, int min, int max) {
	    double span = max - min;
	    double ranged = min + ((value - min) % span + span) % span;
	    return ranged;
	}
	
	
	public static double sind(double degree) {
		return Math.sin(Math.toRadians(degree)); 
	}
	
	public static double cosd(double degree) {
		double rad = Math.toRadians(degree); 
		double cosd = Math.cos(rad); 
		return cosd; 
	}
	
	public static double tand(double degree) {
		return Math.tan(Math.toRadians(degree)); 
	}
	
	public static double cotand(double degree) {
		return 1 / tand(degree); 
	}
	
	public static double asind(double value) { 
		return range(Math.toDegrees(Math.asin(value)), 360);
	}
	
	public static double acosd(double value) { 
		return range(Math.toDegrees(Math.acos(value)), 360);
	}
	
	public static double atand(double value) {
		return range(Math.toDegrees(Math.atan(value)), 360);
	}

	public static double atan2d(double y, double x) {
		return range(Math.toDegrees(Math.atan2(y, x)), 360);
	}

}
