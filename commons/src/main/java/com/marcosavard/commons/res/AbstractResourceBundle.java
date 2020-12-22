package com.marcosavard.commons.res;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class AbstractResourceBundle {
  private Map<Locale, ResourceBundle> bundles = new HashMap<>();

  protected String getResourceString(String key, Locale locale) {
    ResourceBundle bundle = getResourceBundle(locale);
    String string;

    try {
      string = bundle.getString(key);
    } catch (MissingResourceException ex) {
      string = key;
    }

    return string;
  }

  private ResourceBundle getResourceBundle(Locale locale) {
    ResourceBundle bundle = bundles.get(locale);

    if (bundle == null) {
      String baseName = getBaseName();
      bundle = ResourceBundle.getBundle(baseName, locale);
      bundles.put(locale, bundle);
    }

    return bundle;
  }

  private String getBaseName() {
    Class claz = this.getClass();
    String className = claz.getName();
    String baseName = className.replace('.', '/');
    return baseName;
  }



}
