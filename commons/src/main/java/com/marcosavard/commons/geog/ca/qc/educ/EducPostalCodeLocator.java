package com.marcosavard.commons.geog.ca.qc.educ;

import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.ca.PostalCode;
import com.marcosavard.commons.geog.ca.PostalCodeLocator;

/**
 * An implementation that takes postal codes in Quebec school repository to find
 * a location from a given postal code. 
 * 
 * @author Marco
 *
 */
 
public class EducPostalCodeLocator extends PostalCodeLocator {

	public GeoCoordinate findLocation(PostalCode postalCode) {
		EducationalNetwork network = EducationalNetworkReader.read(); 
		GeoCoordinate coordinate = network.findNearestSchoolFrom(postalCode).getCoordinate(); 
		return coordinate;
	}

}