package com.marcosavard.commons.math;

import java.util.Locale;
import java.util.ResourceBundle;

public interface RessourceEnum {

  String name();

  public default String getDisplayName(Locale display) {
    String baseName = getClass().getName();
    ResourceBundle bundle = ResourceBundle.getBundle(baseName, display);
    String key = name();
    String displayName = bundle.containsKey(key) ? bundle.getString(key) : name().toLowerCase();
    return displayName;
  }
}
