package com.marcosavard.commons.phy;

import java.text.MessageFormat;
import java.util.Locale;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.phy.res.PhysicalUnitBundle;

// display locale-sensible units of measurements
// PhysicalUnitBundle.getString("km");
// PhysicalUnitBundle.getString("s");
//
// TODO
// String km = LocaleManager.KM; // = "km"
// 1 kilometre
// 2 kilometres
// 0 kilometre
// si : km, gr, s, mn, hr, days
// us: in, ft, yd, mi
public class PhysicalUnitDemo {

  public static void main(String[] args) {
    formatTimeDemo();
    formatLengthDemo();
  }

  private static void formatTimeDemo() {
    Locale locale = Locale.ENGLISH;
    String formatted = formatTime(1, 15, 30, locale);
    Console.println(formatted);

    locale = Locale.FRENCH;
    formatted = formatTime(1, 15, 30, locale);
    Console.println(formatted);
    Console.println();
  }

  private static void formatLengthDemo() {
    Locale locale = Locale.ENGLISH;
    String formatted = formatLength(1, 15, locale);
    Console.println(formatted);

    locale = Locale.FRENCH;
    formatted = formatLength(1, 15, locale);
    Console.println(formatted);
    Console.println();
  }

  private static String formatLength(int km, int m, Locale locale) {
    String kmkey = km < 2 ? "kilometer" : "kilometers";
    String mkey = m < 2 ? "meter" : "meters";

    String kms = PhysicalUnitBundle.getString(kmkey, locale);
    String ms = PhysicalUnitBundle.getString(mkey, locale);

    String formatted = MessageFormat.format("{0} {1}, {2} {3}", km, kms, m, ms);
    return formatted;
  }

  private static String formatTime(int h, int m, int s, Locale locale) {
    String hkey = h < 2 ? "hour" : "hours";
    String mkey = m < 2 ? "minute" : "minutes";
    String skey = s < 2 ? "second" : "seconds";

    String hs = PhysicalUnitBundle.getString(hkey, locale);
    String ms = PhysicalUnitBundle.getString(mkey, locale);
    String ss = PhysicalUnitBundle.getString(skey, locale);

    String formatted = MessageFormat.format("{0} {1}, {2} {3}, {4} {5}", h, hs, m, ms, s, ss);
    return formatted;
  }

}
