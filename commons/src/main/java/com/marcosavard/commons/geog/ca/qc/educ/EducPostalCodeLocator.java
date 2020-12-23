package com.marcosavard.commons.geog.ca.qc.educ;

import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.geog.ca.PostalCode;
import com.marcosavard.commons.geog.ca.PostalCodeLocator;

/**
 * An implementation that takes postal codes in Quebec school repository to find a location from a
 * given postal code.
 * 
 * @author Marco
 *
 */

public class EducPostalCodeLocator extends PostalCodeLocator {

  @Override
  public GeoLocation findLocation(PostalCode postalCode) {
    EducationalNetwork network = EducationalNetworkReader.read();
    GeoLocation coordinate = network.findNearestSchoolFrom(postalCode).getCoordinate();
    return coordinate;
  }

  @Override
  public PostalCode findPostalCode(GeoLocation location) {
    EducationalNetwork network = EducationalNetworkReader.read();
    PostalCode postalCode = network.findNearestPostalCode(location);
    return postalCode;
  }

}
