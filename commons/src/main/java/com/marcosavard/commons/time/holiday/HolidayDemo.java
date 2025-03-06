package com.marcosavard.commons.time.holiday;

import com.marcosavard.commons.debug.Console;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HolidayDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to = LocalDate.of(2025, 12, 31);

       // printHolidaysOfYear(2025, display);
        printHolidaysInPeriod(from, to, display);
    }

    private static void printHolidaysOfYear(int year, Locale display) {
        List<Holiday.Event> holidays = new ArrayList<>();
        holidays.addAll(Arrays.asList(FloatingHoliday.getHolidaysOf(year)));
        holidays.addAll(Arrays.asList(NationalHoliday.getHolidaysOf(year)));
        holidays.addAll(Arrays.asList(ReligiousHoliday.getHolidaysOf(year)));
        Holiday.sort(holidays);

        for (Holiday.Event event : holidays) {
            String displayName = event.holiday.getDisplayName(display);
            Console.println("{0} : {1}", displayName, event.date);
        }
    }

    private static void printHolidaysInPeriod(LocalDate from, LocalDate to, Locale display) {
        ReligiousHoliday.Event[] holidayEvents = ReligiousHoliday.findHolidayEvents(from, to);

        for (ReligiousHoliday.Event event : holidayEvents) {
            String displayName = event.holiday.getDisplayName(display);
            Console.println("{0} : {1}", displayName, event.date);
        }
    }


}
