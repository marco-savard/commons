package com.marcosavard.commons.geog.res;

import java.util.Locale;
import com.marcosavard.commons.res.CommonResourceBundle;

public class CountryTimeZoneBundle extends CommonResourceBundle {
  private static CountryTimeZoneBundle bundle;

  public static CountryTimeZoneBundle getBundle() {
    if (bundle == null) {
      bundle = new CountryTimeZoneBundle();
    }

    return bundle;
  }

  public static String getString(String key, Locale locale) {
    return getBundle().getResourceString(key, locale);
  }
}
