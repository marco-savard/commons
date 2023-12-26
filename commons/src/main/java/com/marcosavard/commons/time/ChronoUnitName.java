package com.marcosavard.commons.time;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

public enum ChronoUnitName {
  NANOS,
  MICROS,
  MILLIS,
  SECONDS(ChronoField.SECOND_OF_MINUTE),
  MINUTES(ChronoField.MINUTE_OF_HOUR),
  HOURS(ChronoField.HOUR_OF_DAY),
  HALF_DAYS,
  DAYS,
  WEEKS,
  MONTHS(ChronoField.MONTH_OF_YEAR),
  YEARS(ChronoField.YEAR),
  DECADES,
  CENTURIES,
  MILLENNIA,
  ERAS(ChronoField.ERA),
  FOREVER;

  private ChronoField chronoField;

  ChronoUnitName() {
    this.chronoField = null;
  }

  ChronoUnitName(ChronoField chronoField) {
    this.chronoField = chronoField;
  }

  public static ChronoUnitName of(ChronoUnit cu) {
    return ChronoUnitName.values()[cu.ordinal()];
  }

  @Override
  public String toString() {
    return this.name();
  }

  public String getDisplayName(Locale display) {
    Class claz = ChronoUnitName.class;
    String basename = claz.getName().replace('.', '/');
    ResourceBundle bundle = ResourceBundle.getBundle(basename, display);
    String name = this.name().toLowerCase();
    String displayName = bundle.containsKey(name) ? bundle.getString(name) : findName(display);
    return displayName;
  }

  private String findName(Locale display) {
    return (chronoField != null) ? chronoField.getDisplayName(display) : findName();
  }

  private String findName() {
    return this.name();
  }
}
