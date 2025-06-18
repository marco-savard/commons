package com.marcosavard.common.time;

import com.marcosavard.common.debug.Console;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class JulianDayDemo {
    public static void main(String[] args) {
         demo();
    }

    private static void demo() {
        for (int y = 1998; y<=2021; y++) {
            LocalDate date = LocalDate.of(y, Month.JANUARY, 1).minus(1, ChronoUnit.DAYS);
            double jd = JulianDay.daysSinceJ2000(date);
            Console.println("{0} -> {1}", Integer.toString(y), Double.toString(jd));
        }

        LocalDate date1 = LocalDate.of(1998, Month.AUGUST, 10);
        ZonedDateTime time1 = ZonedDateTime.of(date1, LocalTime.of(23, 10), ZoneOffset.UTC);
        double jd = JulianDay.daysSinceJ2000(time1);
        Console.println("{0} -> {1} = {2}", date1, Double.toString(jd), time1);
    }
}
