package com.marcosavard.commons.astro.zodiac;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ZodiacSign {
  ARIES,
  TAURUS,
  GEMINI,
  CANCER,
  LEO,
  VIRGO,
  LIBRA,
  SCORPIO,
  SAGITTARIUS,
  CAPRICORN,
  AQUARIUS,
  PISCES;

  private static final int ARIES_SYMBOL = 0x2648;

  public String getDisplayName(Locale locale) {
    Class claz = ZodiacSign.class;
    String basename = claz.getName().replace('.', '/');
    ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
    String displayName = bundle.getString(this.name());
    return displayName;
  }

  public char getSymbol() {
    return (char) (this.ordinal() + ARIES_SYMBOL);
  }
}
