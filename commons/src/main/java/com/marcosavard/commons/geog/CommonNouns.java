package com.marcosavard.commons.geog;

import java.util.Locale;

public class CommonNouns {

  private CountryGlossary countryGlossary = new CountryGlossary();
  private TimeZoneGlossary timeZoneGlossary = new TimeZoneGlossary();

  public String getIslandWord(Locale display) {
    return countryGlossary.getIslandWord(display);
  }

  public String getIslandsWord(Locale display) {
    return countryGlossary.getIslandsWord(display);
  }

  public String getOceanWord(Locale display) {
    return timeZoneGlossary.getOceanWord(display);
  }
}
