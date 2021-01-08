package com.marcosavard.commons.res;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class CommonResourceBundle {
  private Map<Locale, List<ResourceBundle>> bundlesByLocale = new HashMap<>();

  protected String getResourceString(String key, Locale locale) {
    List<ResourceBundle> bundles = getResourceBundle(locale);
    String string = null;

    for (ResourceBundle bundle : bundles) {
      try {
        string = bundle.getString(key);
        break; // found the string: stop to iterate
      } catch (MissingResourceException ex) {
        // if resource is missing, continue to search in other bundles
      }
    }

    if (string == null) {
      string = "[" + key + "]";
    }

    return string;
  }

  private List<ResourceBundle> getResourceBundle(Locale locale) {
    List<ResourceBundle> bundles = bundlesByLocale.get(locale);

    if (bundles == null) {
      bundles = new ArrayList<>();
      bundlesByLocale.put(locale, bundles);
      List<String> basenames = getBaseNames();

      for (String basename : basenames) {
        try {
          ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
          bundles.add(bundle);
        } catch (MissingResourceException ex) {
          // do nothing, continue to iterate
        }
      }
    }

    return bundles;
  }

  private List<String> getBaseNames() {
    List<String> basenames = new ArrayList<>();
    Class claz = this.getClass();

    while (claz != null) {
      String className = claz.getName();
      String basename = className.replace('.', '/');
      basenames.add(basename);
      claz = claz.getSuperclass();
    }

    return basenames;
  }



}
