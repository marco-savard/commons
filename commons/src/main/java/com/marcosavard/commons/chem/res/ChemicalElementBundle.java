package com.marcosavard.commons.chem.res;

import java.util.Locale;
import com.marcosavard.commons.res.CommonResourceBundle;

public class ChemicalElementBundle extends CommonResourceBundle {
  private static ChemicalElementBundle bundle;

  public static ChemicalElementBundle getBundle() {
    if (bundle == null) {
      bundle = new ChemicalElementBundle();
    }

    return bundle;
  }

  public static String getString(String key, Locale locale) {
    return getBundle().getResourceString(key, locale);
  }
}
