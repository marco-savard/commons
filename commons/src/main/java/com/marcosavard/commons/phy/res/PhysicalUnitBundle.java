package com.marcosavard.commons.phy.res;

import java.util.Locale;
import com.marcosavard.commons.res.CommonResourceBundle;

public class PhysicalUnitBundle extends CommonResourceBundle {
  private static PhysicalUnitBundle bundle;

  public static PhysicalUnitBundle getBundle() {
    if (bundle == null) {
      bundle = new PhysicalUnitBundle();
    }

    return bundle;
  }

  public static String getString(String key, Locale locale) {
    return getBundle().getResourceString(key, locale);
  }
}
