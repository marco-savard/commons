package com.marcosavard.commons.time;

import com.marcosavard.commons.debug.Console;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static com.marcosavard.commons.time.JulianDay.*;

public class JulianDayTest {

    @Test
    void givenJd0_whenToLocalDateTime_thenReturnJulianDayEra() {
        LocalDateTime dateTime = JulianDay.toLocalDateTime(0);
        Assert.assertEquals(LocalDateTime.of(JULIAN_DAY_ERA, LocalTime.NOON), dateTime);
    }

    @Test
    void givenLocalDate2000Midnight_whenToJulianDay_thenReturnJdMidnight() {
        LocalDateTime dateTime = LocalDateTime.of(Y2000, LocalTime.MIDNIGHT);
        double jd = JulianDay.toJulianDay(dateTime);
        Assert.assertEquals(JULIAN_DAY_Y2000_MIDNIGHT_UTC, jd);
    }

    @Test
    void givenLocalDate2000Noon_whenToJulianDay_thenReturnJdNoon() {
        LocalDateTime dateTime = LocalDateTime.of(Y2000, LocalTime.NOON);
        double jd = JulianDay.toJulianDay(dateTime);
        Assert.assertEquals(JULIAN_DAY_Y2000_NOON_UTC, jd);
    }

    @Test
    void givenJdMidnight_whenToLocalDate_thenReturnLocalDate2000Midnight() {
        LocalDate date = JulianDay.toLocalDate(JULIAN_DAY_Y2000_MIDNIGHT_UTC);
        Assert.assertEquals(Y2000, date);
    }

    @Test
    void givenJdNood_whenToLocalDate_thenReturnLocalDate2000Noon() {
        LocalDate date = JulianDay.toLocalDate(JULIAN_DAY_Y2000_NOON_UTC);
        Assert.assertEquals(Y2000, date);
    }

    @Test
    void givenJdMidnight_whenToLocalDateTime_thenReturnLocalDateTime2000Midnight() {
        LocalDateTime dateTime = JulianDay.toLocalDateTime(JULIAN_DAY_Y2000_MIDNIGHT_UTC);
        Assert.assertEquals(LocalDateTime.of(Y2000, LocalTime.MIDNIGHT), dateTime);
    }

    @Test
    void givenJdNoon_whenToLocalDateTime_thenReturnLocalDateTime2000Noon() {
        LocalDateTime dateTime = JulianDay.toLocalDateTime(JULIAN_DAY_Y2000_NOON_UTC);
        Assert.assertEquals(LocalDateTime.of(Y2000, LocalTime.NOON), dateTime);
    }

    @Test
    void givenLocalDates_whenToJulianDate_thenReturnCorrectJds() {
        Assert.assertEquals(toJulianDay(1968, Month.MAY, 23), 2439999.5);
        Assert.assertEquals(toJulianDay(1969, Month.JULY, 21), 2440423.5);
        Assert.assertEquals(toJulianDay(1980, Month.JANUARY, 1), 2444239.5);
        Assert.assertEquals(toJulianDay(1982, Month.JANUARY, 1), 2444970.5);
    }

    @Test
    void givenLocalDates_whenToJulianDateLegacy_thenReturnCorrectJds() {
        Assert.assertEquals(toJulianDayLegacy(1968, Month.MAY, 23), 2439999.5);
        Assert.assertEquals(toJulianDayLegacy(1969, Month.JULY, 21), 2440423.5);
        Assert.assertEquals(toJulianDayLegacy(1980, Month.JANUARY, 1), 2444239.5);
        Assert.assertEquals(toJulianDayLegacy(1982, Month.JANUARY, 1), 2444970.5);
    }

    @Test
    void givenJds_whenToLocalDate_thenReturnCorrectLocalDates() {
        Assert.assertEquals(toLocalDate(2439999.5), LocalDate.of(1968, Month.MAY, 23));
        Assert.assertEquals(toLocalDate(2440423.5), LocalDate.of(1969, Month.JULY, 21));
        Assert.assertEquals(toLocalDate(2444239.5), LocalDate.of(1980, Month.JANUARY, 1));
        Assert.assertEquals(toLocalDate(2444970.5), LocalDate.of(1982, Month.JANUARY, 1));
    }

    @Test
    void givenJds_whenToLocalDateLegacy_thenReturnCorrectLocalDates() {
        Assert.assertEquals(toLocalDateLegacy(2439999.5), LocalDate.of(1968, Month.MAY, 23));
        Assert.assertEquals(toLocalDateLegacy(2440423.5), LocalDate.of(1969, Month.JULY, 21));
        Assert.assertEquals(toLocalDateLegacy(2444239.5), LocalDate.of(1980, Month.JANUARY, 1));
        Assert.assertEquals(toLocalDateLegacy(2444970.5), LocalDate.of(1982, Month.JANUARY, 1));
    }

    @Test
    void testPerformance() {
        int n = 1_000_000;
        long start = System.currentTimeMillis();

        for (int i=0; i<n; i++) {
            Assert.assertEquals(toJulianDay(1968, Month.MAY, 23), 2439999.5);
        }

        long duration = System.currentTimeMillis() - start;
        Console.println("Duration : {0} ms", duration);
        start = System.currentTimeMillis();

        for (int i=0; i<n; i++) {
            Assert.assertEquals(toJulianDayLegacy(1968, Month.MAY, 23), 2439999.5);
        }

        duration = System.currentTimeMillis() - start;
        Console.println("Duration (legacy) : {0} ms", duration);
    }

    private LocalDate toLocalDateLegacy(double jd) {
        return JulianDay.toLocalDateLegacy(jd);
    }

    private LocalDate toLocalDate(double jd) {
        return JulianDay.toLocalDate(jd);
    }

    private double toJulianDay(int year, Month month, int dayOfMonth) {
        return JulianDay.toJulianDay(LocalDate.of(year, month, dayOfMonth));
    }

    private double toJulianDayLegacy(int year, Month month, int dayOfMonth) {
        return JulianDay.toJulianDayLegacy(year, month.getValue(), dayOfMonth);
    }
}
