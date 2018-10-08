package com.marcosavard.commons.geog.ca;

import java.text.MessageFormat;

import com.marcosavard.commons.geog.GeoCoordinate;

public class PostalCodeGeoCoordinateDemo {
	
	public static void main(String[] args) {
		PostalCode postalCode = new PostalCode("G3E 2C7");
		GeoCoordinate coord = PostalCodeGeoCoordinate.findLocation(postalCode); 
		String msg = MessageFormat.format("  {0} : coord: {1}", postalCode.toDisplayString(), coord); 
		System.out.println(msg);
	}

}
