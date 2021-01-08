package com.marcosavard.commons.time.res;

import java.util.Locale;
import com.marcosavard.commons.res.CommonResourceBundle;

public class TimeResources extends CommonResourceBundle {
  private static TimeResources bundle;

  public static TimeResources getBundle() {
    if (bundle == null) {
      bundle = new TimeResources();
    }

    return bundle;
  }

  public static String getString(String key) {
    return getString(key, Locale.getDefault());
  }

  public static String getString(String key, Locale locale) {
    return getBundle().getResourceString(key, locale);
  }

}
