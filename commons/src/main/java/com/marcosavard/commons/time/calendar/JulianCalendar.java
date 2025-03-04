package com.marcosavard.commons.time.calendar;

import java.time.LocalDate;
import java.time.Month;

public class JulianCalendar {
    //gregorian reform took place on 15th October 1582
    private static final LocalDate GREGORIAN_REFORM_DATE = LocalDate.of(1582, Month.OCTOBER, 15);

    public static LocalDate toJulianDate(LocalDate gregorianDate) {
        // adjust the Gregorian date to the Julian date
        int[] gregorianDaysInMonth = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] julianDaysInMonth = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int year = gregorianDate.getYear();
        int month = gregorianDate.getMonthValue();
        int day = gregorianDate.getDayOfMonth();

        // check if the year is a leap year
        boolean gregorianLeap = isGregorianLeapYear(year);
        boolean julianLeap = isJulianLeapYear(year);
        gregorianDaysInMonth[1] = gregorianLeap ? 29 : 28;
        julianDaysInMonth[1] = julianLeap ? 29 : 28;

        int julianOffset = findJulianOffset(gregorianDate);
        day -= julianOffset; // subtract the offset from the day

        // adjust month and year if day is out of range
        while (day < 1) {
            month--;
            if (month < 1) {
                month = 12;
                year--;
            }
            day += julianDaysInMonth[month - 1];
        }

        return LocalDate.of(year, month, day);
    }

    private static boolean isJulianLeapYear(int year) {
        return year % 4 == 0;
    }

    private static boolean isGregorianLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }

    private static int findJulianOffset(LocalDate localDate) {
        int year = localDate.getYear();
        boolean isAfterGregorianReform = localDate.isEqual(GREGORIAN_REFORM_DATE) || localDate.isAfter(GREGORIAN_REFORM_DATE);
        int julianOffset = 0; //no offset

        if (isAfterGregorianReform) {
            // difference increases by 1 day every century (except for years divisible by 400)
            julianOffset = 10 + ((year - 1600) / 100) - ((year - 1600) / 400);
        }

        return julianOffset;
    }
}
