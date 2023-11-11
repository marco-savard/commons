package com.marcosavard.commons.geog;

import java.time.format.TextStyle;
import java.util.Locale;

public enum CardinalPoint {
  NORTH,
  NORTH_NORTH_EAST,
  NORTH_EAST,
  EAST_NORTH_EAST,
  EAST,
  EAST_SOUTH_EAST,
  SOUTH_EAST,
  SOUTH_SOUTH_EAST,
  SOUTH,
  SOUTH_SOUTH_WEST,
  SOUTH_WEST,
  WEST_SOUTH_WEST,
  WEST,
  WEST_NORTH_WEST,
  NORTH_WEST,
  NORTH_NORTH_WEST;

  private CountryGlossary countryGlossary = new CountryGlossary();
  private TimeZoneGlossary timeZoneGlossary = new TimeZoneGlossary();

  public String getDisplayName(TextStyle textStyle, Locale locale) {
    String displayName = "?";
    String north = countryGlossary.getNorthWord(locale);
    String east = timeZoneGlossary.getEastWord(locale);
    String south = countryGlossary.getSouthWord(locale);
    String west = timeZoneGlossary.getWestWord(locale);
    north = textStyle.ordinal() >= TextStyle.SHORT.ordinal() ? north.substring(0, 1) : north;
    east = textStyle.ordinal() >= TextStyle.SHORT.ordinal() ? east.substring(0, 1) : east;
    south = textStyle.ordinal() >= TextStyle.SHORT.ordinal() ? south.substring(0, 1) : south;
    west = textStyle.ordinal() >= TextStyle.SHORT.ordinal() ? west.substring(0, 1) : west;
    String sep = textStyle.ordinal() >= TextStyle.SHORT.ordinal() ? "" : "-";

    if (ordinal() == NORTH.ordinal()) {
      displayName = north;
    } else if (ordinal() == NORTH_NORTH_EAST.ordinal()) {
      displayName = north + sep + north + sep + east;
    } else if (ordinal() == NORTH_EAST.ordinal()) {
      displayName = north + sep + east;
    } else if (ordinal() == EAST_NORTH_EAST.ordinal()) {
      displayName = east + sep + north + sep + east;
    } else if (ordinal() == EAST.ordinal()) {
      displayName = east;
    } else if (ordinal() == EAST_SOUTH_EAST.ordinal()) {
      displayName = east + sep + south + sep + east;
    } else if (ordinal() == SOUTH_EAST.ordinal()) {
      displayName = south + sep + east;
    } else if (ordinal() == SOUTH_SOUTH_EAST.ordinal()) {
      displayName = south + sep + south + sep + east;
    } else if (ordinal() == SOUTH.ordinal()) {
      displayName = south;
    } else if (ordinal() == SOUTH_SOUTH_WEST.ordinal()) {
      displayName = south + sep + south + sep + west;
    } else if (ordinal() == SOUTH_WEST.ordinal()) {
      displayName = south + sep + west;
    } else if (ordinal() == WEST_SOUTH_WEST.ordinal()) {
      displayName = west + sep + south + sep + west;
    } else if (ordinal() == WEST.ordinal()) {
      displayName = west;
    } else if (ordinal() == WEST_NORTH_WEST.ordinal()) {
      displayName = west + sep + north + sep + west;
    } else if (ordinal() == NORTH_WEST.ordinal()) {
      displayName = north + sep + west;
    } else if (ordinal() == NORTH_NORTH_WEST.ordinal()) {
      displayName = north + sep + north + sep + west;
    }

    return displayName;
  }
}
