package com.marcosavard.commons.time.calendar;

import com.marcosavard.commons.debug.Console;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MonthDemo {

    public static void main(String[] args) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMMM");
        Month month = Month.JANUARY;
        LocalDate date = LocalDate.of(2025, Month.JANUARY, 1);

        for (Locale loc : Locale.getAvailableLocales()) {
            Console.println("{0} : {1}", loc, fmt.withLocale(loc).format(date));
        }
    }
}
