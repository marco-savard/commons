package com.marcosavard.commons.time;

import java.time.LocalTime;

public class TimeConversion {

  public static LocalTime toLocalTime(double fractionOfDay) {
    int hour = (int) Math.floor(fractionOfDay * 24.0);
    double fractionOfHour = (fractionOfDay * 24) - hour;
    int minute = (int) Math.floor(fractionOfHour * 60.0);
    double fractionOfMinutes = (fractionOfHour * 60) - minute;
    int second = (int) Math.floor(fractionOfMinutes * 60.0);
    LocalTime localTime = LocalTime.of(hour, minute, second);
    return localTime;
  }

}
