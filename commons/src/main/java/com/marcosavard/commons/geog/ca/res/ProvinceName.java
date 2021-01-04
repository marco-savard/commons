package com.marcosavard.commons.geog.ca.res;

import java.util.Locale;
import com.marcosavard.commons.res.CommonResourceBundle;

public class ProvinceName extends CommonResourceBundle {
  private static ProvinceName bundle;

  public static ProvinceName getBundle() {
    if (bundle == null) {
      bundle = new ProvinceName();
    }

    return bundle;
  }

  public static String getString(String key, Locale locale) {
    return getBundle().getResourceString(key, locale);
  }
}
