package com.marcosavard.commons.geog.ca;

import java.text.MessageFormat;

import com.marcosavard.commons.geog.GeoCoordinate;

public class PostalCodeLocatorDemo {
	
	public static void main(String[] args) {
		PostalCode postalCode = new PostalCode("G3E 2C7");
		PostalCodeLocator locator = new SimplePostalCodeLocator();
		GeoCoordinate coord = locator.findLocation(postalCode); 
		String msg = MessageFormat.format("  {0} : coord: {1}", postalCode.toDisplayString(), coord); 
		System.out.println(msg);
	}

}