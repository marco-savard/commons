package com.marcosavard.commons.geog;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;

/** A class that represent geographical coordinates (latitude and longitude). 
 *  It provides methods to display coordinates in decimal or degrees/minutes/seconds
 *  formats. It computes distance to another geographical coordinate.  This
 *  class is immutable (values are set at construction, and cannot be modified
 *  during the lifetime of an instance.
 *  
 * @author Marco
 *
 */
@SuppressWarnings("serial")
public class GeoCoordinate implements Serializable {
	public enum Format {DECIMAL, DEG_MIN_SEC, DEG_MIN_SEC_HTML}; 

	private static final double EARTH_RADIUS = 6371.01; //mean radius in km

	private double _lat, _lon;
	
	//required by GWT
	@SuppressWarnings("unused")
	
	/**
	 * Create coordinates by decimal latitude and longitude 
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public GeoCoordinate(double latitude, double longitude) {
		_lat = latitude;
		_lon = longitude;
	}
	
	/**
	 * Create coordinates by latitude and longitude given in degrees, minutes and seconds. 
	 * 
	 * @param latitudeDeg latitude in degree
	 * @param latitudeMin latitude in minute
	 * @param latitudeSec latitude in second
	 * @param longitudeDeg longitude in degree
	 * @param longitudeMin longitude in minute
	 * @param longitudeSec longitude in second
	 */
	public GeoCoordinate(int latitudeDeg, int latitudeMin, int latitudeSec, int longitudeDeg, int longitudeMin, int longitudeSec) {
		double latitudeDec = (latitudeMin/60.0) + (latitudeSec/3600.0); 
		double longitudeDec = (longitudeMin/60.0) + (longitudeSec/3600.0); 
		_lat = latitudeDeg + Math.signum(latitudeDeg) * latitudeDec; 
		_lon = longitudeDeg + Math.signum(longitudeDec) * longitudeDec; 
	}


	public double getLatitude() { return _lat; }
	
	public double getLongitude() { return _lon; }
	
	@Override 
	public String toString() {
		String msg = MessageFormat.format("{0}, {1}", toLatitudeString(), toLongitudeString()); 
		return msg;
	}
	
	/**
	 * Return textual display of a coordinate, according a given format 
	 *  
	 *  @param format one of DECIMAL, DEG_MIN_SEC and DEG_MIN_SEC_HTML
	 * @return textual display of a coordinat
	 */	
	public String toDisplayString(Format format) {
		String ns = getLatHemisphere();
		String ew = getLonHemisphere();
		String pat; 
		
		if (format == Format.DECIMAL) {
			return toString(); 
		} else if (format == Format.DEG_MIN_SEC) {
			pat = "{0}\u00B0{1}''{2}\" {3}";
		} else if (format == Format.DEG_MIN_SEC_HTML) {
			pat = "{0}&deg;{1}&prime;{2}&Prime; {3}"; 
		} else {
			pat = "Undefined Format";
		}
		
		String lat = toDegMinSecString(pat, _lat, ns); 
		String lon = toDegMinSecString(pat, _lon, ew); 
		String str = MessageFormat.format("{0}, {1}", lat, lon); 
		
		return str;
	}
	
	public String toDisplayString() {
		return toDisplayString(Format.DEG_MIN_SEC);
	}
	
	public String toLatitudeString() {
		String ns = _lat > 0 ? "N" : "S"; 
		String text = String.format("%2.2f ", Math.abs(_lat)) + ns;
		return text;
	}

	public String toLongitudeString() {
		String ew = _lon > 0 ? "E" : "W"; 
		String text = String.format("%2.2f ", Math.abs(_lon)) + ew;
		return text;
	}
	
	/**
	 * Compute the distance, in kilometers, between that location. 
	 * 
	 * @param location to which distance is computed
	 * @return the distance in kilometers 
	 */
	public double computeDistanceFrom(GeoCoordinate location) {
		if (location == null) {
			return 0;
		}
		
		double lat0 = toRadians(location.getLatitude()); 
		double lon0 = toRadians(location.getLongitude());
		double lat1 = toRadians(getLatitude()); 
		double lon1 = toRadians(getLongitude());
		
		double deltaLon = Math.abs(lon0 - lon1); 
		double greatCircle = (Math.sin(lat0) * Math.sin(lat1)) + (Math.cos(lat0) * Math.cos(lat1) * Math.cos(deltaLon)); 
		double deltaAngle = Math.acos(greatCircle); 
		double distance = EARTH_RADIUS * deltaAngle;

		return distance;
	}
	
	//
	// private methods
	//
	
	private String getLatHemisphere() {
		String ns = (_lat >= 0) ? "N" : "S";
		return ns;
	}
	
	private String getLonHemisphere() {
		return getLonHemisphere(Locale.getDefault());
	}
	
	private String getLonHemisphere(Locale locale) {
		String ew;
		
		if (locale.getLanguage().equals(Locale.FRENCH)) {
			ew = (_lon >= 0) ? "E" : "O";
		} else if (locale.getLanguage().equals(Locale.GERMAN)) {
			ew = (_lon >= 0) ? "O" : "W";
		} else {
			ew = (_lon >= 0) ? "E" : "W";
		}
		
		return ew;
	}
		
	private String toDegMinSecString(String pat, double coord, String hemis) {
		coord = Math.abs(coord);
		int deg = (int)Math.floor(coord);
		int min = (int)Math.floor((60 * coord) - (60 * deg));
		int sec = (int)Math.floor((60*60*coord) - (60*60*deg) - (60*min));
		String str = MessageFormat.format(pat, deg, min, sec, hemis); 
		return str;
	}
	
	
	private double toRadians(double degs) {
		double rads = degs * Math.PI / 180.0;
		return rads;
	}



	


}
