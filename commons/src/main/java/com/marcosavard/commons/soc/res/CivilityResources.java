package com.marcosavard.commons.soc.res;

import java.util.Locale;
import com.marcosavard.commons.res.CommonResourceBundle;

public class CivilityResources extends CommonResourceBundle {
  private static CivilityResources bundle;

  public static CivilityResources getBundle() {
    if (bundle == null) {
      bundle = new CivilityResources();
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
