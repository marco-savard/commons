package com.marcosavard.commons.time;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class TimeUnitName {
  private TimeUnit unit;

  public static TimeUnitName of(TimeUnit unit) {
    return new TimeUnitName(unit);
  }

  private TimeUnitName(TimeUnit unit) {
    this.unit = unit;
  }

  public String getDisplayName(Locale locale) {
    Class claz = TimeUnitName.class;
    String basename = claz.getName().replace('.', '/');
    ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
    String name = unit.name().toLowerCase();
    name = name.substring(0, name.length() - 1);
    String displayName = bundle.getString(name);
    return displayName;
  }
}
