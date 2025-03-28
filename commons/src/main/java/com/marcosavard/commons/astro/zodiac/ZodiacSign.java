package com.marcosavard.commons.astro.zodiac;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public enum ZodiacSign {
  ARIES(Month.APRIL, 15),
  TAURUS(Month.MAY, 16),
  GEMINI(Month.JUNE, 16),
  CANCER(Month.JULY, 16),
  LEO(Month.AUGUST, 16),
  VIRGO(Month.SEPTEMBER, 16),
  LIBRA(Month.OCTOBER, 16),
  SCORPIO(Month.NOVEMBER, 16),
  SAGITTARIUS(Month.DECEMBER, 16),
  CAPRICORN(Month.JANUARY, 15),
  AQUARIUS(Month.FEBRUARY, 15),
  PISCES(Month.MARCH, 15);

  private static final int ARIES_SYMBOL = 0x2648;
  private final Month month;
  private final int dayOfMonth;

  ZodiacSign(Month month, int dayOfMonth) {
    this.month = month;
    this.dayOfMonth = dayOfMonth;
  }

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

  public static ZodiacSign of(Month month, int dayOfMonth) {
    ZodiacSign found = null;

    for (ZodiacSign sign : ZodiacSign.values()) {
      ZodiacSign nextSign = sign.getNext();

      if ((month == sign.month) && (dayOfMonth >= sign.dayOfMonth) || (month == nextSign.month) && (dayOfMonth < nextSign.dayOfMonth)) {
        found = sign;
        break;
      }
    }

    return found;
  }

  public ZodiacSign getNext() {
    int nextIdx = (this.ordinal() + 1) % ZodiacSign.values().length;
    ZodiacSign nextSign = ZodiacSign.values()[nextIdx];
    return nextSign;
  }

  public static ZodiacSign ofOld(Month month, int dayOfMonth) {
    return Arrays.stream(ZodiacSign.values())
        .filter(z -> z.ofDate(month, dayOfMonth))
        .findFirst()
        .orElse(null);
  }

  private boolean ofDate(Month month, int dayOfMonth) {
    int nextIdx = (this.ordinal() + 1) % ZodiacSign.values().length;
    ZodiacSign nextSign = ZodiacSign.values()[nextIdx];
    int year = (nextIdx == 0) ? 2001 : 2000;
    LocalDate date = LocalDate.of(2000, this.month, this.dayOfMonth);
    LocalDate start = LocalDate.of(2000, month, dayOfMonth);
    LocalDate end = LocalDate.of(year, nextSign.month, nextSign.dayOfMonth);
    return !date.isBefore(start) && date.isBefore(end);
  }

  public Month getMonth() {
    return this.month;
  }

  public int getDayOfMonth() {
    return this.dayOfMonth;
  }
}
