package com.marcosavard.commons.geog.ca.qc.educ;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.geog.ca.PostalCode;
import com.marcosavard.commons.geog.ca.PostalCodeLocator;

public class EducPostalCodeLocatorDemo {

  public static void main(String[] args) {
    PostalCodeLocator postalCodeLocator = new EducPostalCodeLocator();

    PostalCode postalCode = PostalCode.of("G1A 1A3");
    GeoLocation location = postalCodeLocator.findLocation(postalCode);
    Console.println("  {0} -> {1}", postalCode.toDisplayString(), location);

    postalCode = postalCodeLocator.findPostalCode(location);
    Console.println("  {0} : Postal Code: {1}", location, postalCode.toDisplayString());
  }



}
