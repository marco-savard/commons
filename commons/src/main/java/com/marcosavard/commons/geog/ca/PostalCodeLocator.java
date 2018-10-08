package com.marcosavard.commons.geog.ca;

import com.marcosavard.commons.geog.GeoCoordinate;

public abstract class PostalCodeLocator {
	
	public abstract GeoCoordinate findLocation(PostalCode code); 

}
