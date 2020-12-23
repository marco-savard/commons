package com.marcosavard.commons.geog.ca;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.GeoLocation;

public class PostalCodeLocatorDemo {

  public static void main(String[] args) {
    findLocationFromPostalCode(PostalCode.of("G1A 1A3"));
    findPostalCodeFromLocation(GeoLocation.of(46.81, -71.23));
  }

  private static void findLocationFromPostalCode(PostalCode postalCode) {
    PostalCodeLocator locator = new SimplePostalCodeLocator();
    GeoLocation coord = locator.findLocation(postalCode);
    Console.println("  {0} : Coordinates : {1}", postalCode.toDisplayString(), coord);
  }

  private static void findPostalCodeFromLocation(GeoLocation location) {
    PostalCodeLocator locator = new SimplePostalCodeLocator();
    PostalCode postalCode = locator.findPostalCode(location);
    Console.println("  {0} : Postal Code : {1}", location, postalCode.toDisplayString());
  }



}
