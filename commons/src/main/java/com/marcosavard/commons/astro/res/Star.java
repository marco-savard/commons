package com.marcosavard.commons.astro.res;

public class Star { 
  private String rank;
  private String constell; 
  private String name;   
  private Double magn; 
  private Double distance; 
  private RightAscension rightAscension;
  private Declination declination;
  
  @Override
  public String toString() {
	  String str = name + " ra=" + rightAscension + ", dec=" + declination; 
	  return str;
  }
  
  public double getMagnitude() {
		return magn;
  }
  
  public double getRightAscension() {
		return rightAscension.hours;
  }
  
  public double getDeclination() {
		return declination.degrees;
  }
  
  public double findDistanceFrom(double ra, double dec) {
	double distance2 = findDistance2From(ra, dec); 
	double distance = Math.sqrt(distance2);
	return distance;
  }
  
  public double findDistance2From(double ra, double dec) {
	double ra2 = (ra - rightAscension.hours) * (ra - rightAscension.hours); 
	double dec2 = (dec - declination.degrees) * (dec - declination.degrees); 
	double distance2 = ra2 + dec2;
	return distance2;
  }
  
  private static String[] stripAlpha(String[] coordinates) {
	  String[] stripped = new String[coordinates.length];
	  
	  for (int i=0; i<coordinates.length; i++) { 
		  stripped[i] = coordinates[i].replaceAll("[^\\d.]", ""); 
	  }
	  
	  return stripped;
  }
  
  public static class RightAscension {
	private double hours;
	  
	public static RightAscension valueOf(String value) { 
		  String[] coordinates = value.split("\\s+");
		  coordinates = stripAlpha(coordinates); 
		  int deg = Integer.parseInt(coordinates[0].trim()); 
		  int min = Integer.parseInt(coordinates[1].trim()); 
		  double sec = Double.parseDouble(coordinates[2].trim()); 
		  RightAscension ra = new RightAscension(deg, min, sec); 
		  return ra; 
	}
	
	private RightAscension(int hour, int min, double sec) {
		hours = hour + (min / 60.0) + (sec / 3600.0); 
	}
	
	@Override
	public String toString() {
		String str = String.format("%.2f", hours);
		return str;
	}
  }
  
  public static class Declination {
		private double degrees;
		  
		public static Declination valueOf(String value) { 
			  String[] coordinates = value.split("\\s+");
			  coordinates = stripAlpha(coordinates); 
			  
			  int deg = Integer.parseInt(coordinates[0].trim()); 
			  int min = Integer.parseInt(coordinates[1].trim()); 
			  double sec = Double.parseDouble(coordinates[2].trim()); 
			  Declination ra = new Declination(deg, min, sec); 
			  return ra; 
		}
		
		private Declination(int deg, int min, double sec) {
			degrees = deg + (min / 60.0) + (sec / 3600.0); 
		}
		
		@Override
		public String toString() {
			String str = String.format("%.2f", degrees);
			return str;
		}
	  }




}
