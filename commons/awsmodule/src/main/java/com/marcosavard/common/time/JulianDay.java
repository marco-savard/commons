package com.marcosavard.common.time;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.JulianFields;

/**
 * Returns the Julian day number that begins at noon of this day, Positive year signifies A.D.,
 * negative year B.C. Remember that the year after 1 B.C. was 1 A.D.
 *
 * <p>ref : Numerical Recipes in C, 2nd ed., Cambridge University Press 1992
 */
public class JulianDay {
    public static final LocalDate JULIAN_DAY_ERA = LocalDate.of(-4713, Month.NOVEMBER, 24);
    public static final LocalDate Y2000 = LocalDate.of(2000, Month.JANUARY, 1);
    public static final double JULIAN_DAY_Y2000_EVE_MIDNIGHT_UTC = 2_451_543.5;
    public static final double JULIAN_DAY_Y2000_EVE_NOON_UTC = 2_451_544.0; //JD of 1999 Dec 31 00:00 UT//JD of 1999 Dec 31 00:00 UT
    public static final double JULIAN_DAY_Y2000_MIDNIGHT_UTC = 2_451_544.5; //JD of 2000 jan 01 00:00 UT
    public static final double JULIAN_DAY_Y2000_NOON_UTC = 2_451_545.0; //JD of 2000 jan 01 12:00 UT

    private static final double DAY_IN_MS = Duration.ofDays(1).toMillis();

    public static double toJulianDay(ZonedDateTime dateTime) {
        LocalTime timeOfDay = dateTime.toLocalTime();
        double jd = dateTime.getLong(JulianFields.JULIAN_DAY);
        jd += timeOfDay.get(ChronoField.MILLI_OF_DAY) / DAY_IN_MS - 0.5;
        return jd;
    }

    public static double toJulianDay(LocalDateTime dateTime) {
        LocalTime timeOfDay = dateTime.toLocalTime();
        double jd = dateTime.getLong(JulianFields.JULIAN_DAY);
        jd += timeOfDay.get(ChronoField.MILLI_OF_DAY) / DAY_IN_MS - 0.5;
        return jd;
    }

    public static double toJulianDay(LocalDate date) {
        return toJulianDay(LocalDateTime.of(date, LocalTime.MIDNIGHT));
    }

    public static double toJulianDay(int year, int month, int dayOfMonth) {
        return toJulianDay(LocalDate.of(year, month, dayOfMonth));
    }

    public static LocalDate toLocalDate(double jd) {
        return LocalDate.MIN.with(JulianFields.JULIAN_DAY, (long)Math.round(jd));
    }

    public static LocalDateTime toLocalDateTime(double jd) {
        long days = (long)(jd + 0.5);
        double fractionOfDay = (jd + 0.5) - days;
        int hour = (int)(fractionOfDay * 24.0);
        int min = (int)((fractionOfDay * 24.0 - hour) * 60.0);
        LocalDate localDate = toLocalDate(days);
        LocalTime localTime = LocalTime.of(hour, min);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return  localDateTime;
    }

    public static double daysSinceJ2000(ZonedDateTime dateTime) {
        double jd = JulianDay.toJulianDay(dateTime) - JulianDay.JULIAN_DAY_Y2000_NOON_UTC;
        return jd;
    }

    public static double daysSinceJ2000(LocalDate date) {
        ZonedDateTime dateTime = ZonedDateTime.of(date, LocalTime.of(0, 0), ZoneOffset.UTC);
        double jd = JulianDay.toJulianDay(dateTime) - JulianDay.JULIAN_DAY_Y2000_NOON_UTC;
        return jd;
    }

    @Deprecated
    public static double toJulianDayLegacy(int year, int month, int dayOfMonth) {
        year = (month <= 2) ? year - 1 : year;
        month = (month <= 2) ? month + 13 : month + 1;
        int a = year / 100;
        int b = a / 4;
        int c = 2 - a + b;
        int e = (int) Math.floor(365.25 * (year + 4716));
        int f = (int) Math.floor(30.6001 * month);
        long jdn = c + dayOfMonth + e + f - 1524;
        return jdn - 0.5;
    }

    @Deprecated
    public static LocalDate toLocalDateLegacy(double jd) {
        double z = Math.floor((jd - 1867216.25) / 36524.25);
        double a = (jd < 2299161) ? jd : jd + 1 + z - Math.floor(z / 4);
        double b = a + 1524;
        double c = Math.floor((b - 122.1) / 365.25);
        double d = Math.floor(c * 365.25);
        int e = (int) Math.floor((b - d) / 30.60001);

        double f = (jd - Math.floor(jd)) + 0.5;
        int dayOfMonth = (int) Math.floor(b - d - Math.floor(30.60001 * e) + f);
        e = (e < 13.5) ? e - 1 : e - 13;
        int month = (int) Math.floor(e);
        int year = (int) Math.floor((e > 2.5) ? c - 4716 : c - 4715);

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        dayOfMonth = (dayOfMonth > daysInMonth) ? daysInMonth : dayOfMonth;
        return LocalDate.of(year, month, dayOfMonth);
    }


}
