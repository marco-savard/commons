package com.marcosavard.commons.time.res;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import com.marcosavard.commons.math.type.Base;
import com.marcosavard.commons.util.Joiner;

public class TimeRemainingDemo {
  private static int[] COUNTDOWN = new int[] { //
      3662, 3661, 3660, 3659, //
      3602, 3601, 3600, 3599, //
      122, 121, 120, 119, //
      62, 61, 60, 59, //
      30, 10, 5, 3, 2, 1};

  private static String[] KEYS = new String[] { //
      "0_hour", "0_hours", //
      "0_minute", "0_minutes", //
      "0_second", "0_seconds"};

  public static void main(String[] args) {
    printTime(Locale.ENGLISH);
    printTime(Locale.FRENCH);
    printTime(Locale.forLanguageTag("es"));
  }

  private static void printTime(Locale locale) {
    print(locale);
    // System.out.println(locale);


    for (int i = 0; i < COUNTDOWN.length; i++) {
      int remaining = COUNTDOWN[i];
      printTimeRemaining(remaining, locale);
    }
    System.out.println();
  }

  private static void print(Object object) {
    Class claz = TimeRemainingDemo.class;

    try {
      Method method = claz.getMethod("print", Object.class);
      Parameter p = method.getParameters()[0];

    } catch (NoSuchMethodException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Parameter p;

  }

  private static void printTimeRemaining(int seconds, Locale locale) {
    Base timeBase = Base.of(24, 60, 60);
    long[] encoded = timeBase.encode(seconds);
    List<String> list = new ArrayList<>();

    for (int i = 0; i < encoded.length; i++) {
      long element = encoded[i];
      if (element > 0) {
        int idx = (i * 2) + (element > 1 ? 1 : 0);
        String key = KEYS[idx];
        String pattern = TimeResources.getString(key, locale);
        String formatted = MessageFormat.format(pattern, element);
        list.add(formatted);
      }
    }

    String suffix = " " + TimeResources.getString("and", locale) + " ";
    String joined = Joiner.ofDelimiter(", ").withSuffix(suffix).join(list);
    long sum = Arrays.stream(encoded).sum();
    String key = (sum < 2) ? "0_remaining" : "0_remaining_pl";
    String pattern = TimeResources.getString(key, locale);
    String formatted = MessageFormat.format(pattern, joined);
    System.out.println("  " + formatted);
  }

}
