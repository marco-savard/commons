package com.marcosavard.commons.time.calendar;

import com.marcosavard.commons.debug.Console;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;

public record VikingDate(LocalDate localDate, int cyclesSinceEra, int yearsInCycle, int dayOfYear) {
    private static final int VIKING_YEAR = 955;
    private static final LocalDate ERA = Season.summerSolsticeOf(VIKING_YEAR).toLocalDate();
    private static final long CYCLE = 364 * 6 + 7;

    public enum WeekDay {
        SUNDAY("Sunnudagr"), //starts on Sunday
        MONDAY("Mánadagr"),
        TUESDAY("Týsdagr"),
        WEDNESDAY("Óðinsdagr"),
        THURSDAY("Þórsdagr"),
        FRIDAY("Frjádagr"),
        SATURDAY("laugardagr");
        private String name;

        WeekDay(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Month {
        HARPA("Harpa"),
        SKERPLA("Skerpla"),
        SOLMANUTHUR("Sólmánuður"),
        HEYANNIR("Heyannir"),
        TVIMANUTHUR("Tvímánuður"),
        HAUSTMANTHUR("Haustmánuður"),
        GORMANUTHUR("Gormánuður"),
        YLIR("Ýlir"),
        MORSUGUR("Mörsugur"),
        THORRI("Þorri"),
        GOA("Góa"),
        EINMANUTHUR("Einmánuður"),
        SUMARAUKI("Sumarauki");
        private String name;

        Month(String name) {
            this.name = name;
        }

        public Object getName() {
            return name;
        }
    }

    private int getYear() {
        return cyclesSinceEra * 6 + yearsInCycle;
    }

    private int getWeekOfYear() {
        return dayOfYear / 7;
    }

    private WeekDay getDayOfWeek() {
        return WeekDay.values()[dayOfYear % 7];
    }

    private static VikingDate ofLocalDate(LocalDate date) {
        long daysSinceEra = Duration.between(ERA.atStartOfDay(), date.atStartOfDay()).toDays();
        int cyclesSinceEra = (int)(daysSinceEra / CYCLE);
        int daysOfCycle = (int)(daysSinceEra % CYCLE);
        int yearsInCycle = daysOfCycle / 364;
        int dayOfYear = daysOfCycle - (yearsInCycle * 364);

        if (yearsInCycle == 6) {
            yearsInCycle = 5;
            dayOfYear += 364;
        }

        return new VikingDate(date, cyclesSinceEra, yearsInCycle, dayOfYear);
    }

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        VikingDate vikingToday = VikingDate.ofLocalDate(today);
        int year = vikingToday.getYear();
        int week = vikingToday.getWeekOfYear();
        WeekDay day = vikingToday.getDayOfWeek();
        Month month = vikingToday.getMonth();
        
        Console.println("{0} -> {1}/{2}/{3} {4}", today, year, week, day.getName(), month.getName());

        for (int i=0; i<4000; i++) {
            LocalDate localDate = ERA.plusDays(i);
            VikingDate vikingDate = VikingDate.ofLocalDate(localDate);
            year = vikingDate.getYear();
            week = vikingDate.getWeekOfYear();
            day = vikingDate.getDayOfWeek();


        }
        //VikingDate vikingDate = new VikingDate(yearsSinceEra, yearInCycle, dayOfYear);



    }

    private Month getMonth() {
        //compute 1st Satuday on or after April 9th
        LocalDate firstDayOfSummer = findFirstDayOfSummer(localDate.getYear());
        firstDayOfSummer = localDate.isBefore(firstDayOfSummer) ? findFirstDayOfSummer(localDate.getYear()-1) : firstDayOfSummer;
        int daysSinceFirstDayOfSummer = (int)Duration.between(firstDayOfSummer.atStartOfDay(), localDate.atStartOfDay()).toDays();
        return Month.values()[daysSinceFirstDayOfSummer / 28];
    }

    private LocalDate findFirstDayOfSummer(int year) {
        LocalDate firstDayOfSummer = LocalDate.of(year, java.time.Month.APRIL, 9);
        DayOfWeek dayOfWeek = firstDayOfSummer.getDayOfWeek();
        return firstDayOfSummer.plusDays(6 - dayOfWeek.getValue());
    }


    public static record VickingDateRecord(int year) {}
}
