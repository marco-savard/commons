package com.marcosavard.commons.geog.ca.qc.educ;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere;
import com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere;
import com.marcosavard.commons.geog.ca.PostalCode;
import com.marcosavard.commons.geog.ca.PostalCodeLocator;

public class EducPostalCodeLocatorDemo {

  public static void main(String[] args) {
    findLocationFromPostalCode(PostalCode.of("G1A 1A3"));
    // 46°47′28″N 071°23′36″W
    LatitudeHemisphere n = LatitudeHemisphere.NORTH;
    LongitudeHemisphere w = LongitudeHemisphere.WEST;
    GeoLocation quebecAirport = GeoLocation.of(46, 47, n, 71, 23, w);
    findPostalCodeFromLocation(quebecAirport);
  }

  private static void findLocationFromPostalCode(PostalCode postalCode) {
    PostalCodeLocator locator = new EducPostalCodeLocator();
    GeoLocation coord = locator.findLocation(postalCode);
    Console.println("  {0} : Coordinates: {1}", postalCode.toDisplayString(), coord);
  }



  private static void findPostalCodeFromLocation(GeoLocation location) {
    PostalCodeLocator locator = new EducPostalCodeLocator();
    PostalCode postalCode = locator.findPostalCode(location);
    Console.println("  {0} : Postal Code: {1}", location, postalCode.toDisplayString());
  }



}
