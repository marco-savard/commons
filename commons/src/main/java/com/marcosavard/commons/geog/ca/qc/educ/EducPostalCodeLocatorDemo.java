package com.marcosavard.commons.geog.ca.qc.educ;

import java.text.MessageFormat;

import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.ca.PostalCode;
import com.marcosavard.commons.geog.ca.PostalCodeLocator;

public class EducPostalCodeLocatorDemo {
	
	public static void main(String[] args) {
		PostalCode postalCode = new PostalCode("G3E 2C7");
		PostalCodeLocator locator = new EducPostalCodeLocator();
		GeoCoordinate coord = locator.findLocation(postalCode); 
		String msg = MessageFormat.format("  {0} : coord: {1}", postalCode.toDisplayString(), coord); 
		System.out.println(msg);
	}

}
