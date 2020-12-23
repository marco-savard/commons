package com.marcosavard.commons.geog.ca.res;

import java.util.Locale;
import com.marcosavard.commons.res.AbstractResourceBundle;

public class CanadianAirport extends AbstractResourceBundle {
  private static CanadianAirport bundle;

  public static CanadianAirport getBundle() {
    if (bundle == null) {
      bundle = new CanadianAirport();
    }

    return bundle;
  }

  public static String getString(String key, Locale locale) {
    return getBundle().getResourceString(key, locale);
  }
}
