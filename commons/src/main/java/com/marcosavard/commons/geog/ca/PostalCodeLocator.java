package com.marcosavard.commons.geog.ca;

import com.marcosavard.commons.geog.GeoCoordinate;

/**
 * A locator that gives an approximate location (latitude/longitude) 
 * for a given postal code. 
 * 
 * @author Marco
 *
 */
public abstract class PostalCodeLocator {
	
	/**
	 * Finds an approximate location (latitude/longitude) for a given postal code. 
	 *
	 * @param code a postal code
	 * @return GeoCoordinate (latitude/longitude) 
	 */
	public abstract GeoCoordinate findLocation(PostalCode code); 
}
