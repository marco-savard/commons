package com.marcosavard.common.geog.ca;

import com.marcosavard.common.util.RessourceEnum;

import java.time.format.TextStyle;
import java.util.Locale;

/**
 * An enumeration for Canadian provinces and territories. Codes follow naming convention of Canada
 * Post.
 *
 * @author Marco
 */
public enum CanadianProvince implements RessourceEnum {
  NL, //
  NS, //
  PE, //
  NB, //
  QC, //
  ON, //
  MB, //
  SK, //
  AB, //
  BC, //
  NU, //
  NT, //
  YK;

  public String getDisplayName(Locale display, TextStyle style) {
    String name = getDisplayName(display);

    if (display.getLanguage().equals(Locale.FRENCH.getLanguage())) {
      return getFrDisplayName(style);
    }

    return name;
  }

  private String getFrDisplayName(TextStyle style) {
    String[] values = getDisplayName(Locale.FRENCH).split(";");
    String displayName = switch (style) {
      case FULL, FULL_STANDALONE -> values[1];
      default -> values[0];
    };

    return displayName;
  }
}
