package com.marcosavard.commons.geog.ca;

import com.marcosavard.commons.debug.Console;

import java.time.format.TextStyle;
import java.util.Locale;

public class CanadianProvinceDemo {

  public static void main(String[] args) {
    Locale display = Locale.FRENCH;

    for (CanadianProvince province : CanadianProvince.values()) {
      Console.println(province.getDisplayName(display, TextStyle.FULL));
    }
  }
}
