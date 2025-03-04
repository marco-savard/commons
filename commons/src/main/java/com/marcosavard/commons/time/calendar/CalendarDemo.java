package com.marcosavard.commons.time.calendar;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendarDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;

        //printEquinoxes();
        printRomanDates(display);
        //printRepublicanDates(display);
    }

    private static void printEquinoxes() {
        for (int y=2020; y<= 2030; y++) {
            LocalDateTime spring = Season.vernalEquinoxOf(y);
            int day = spring.getDayOfMonth();
            int hour = spring.getHour();
            int min = spring.getMinute();
            Console.print("| {0} ", Integer.toString(y));
            Console.print("| {0} | {1}:{2}", day, hour, min);

            LocalDateTime summer = Season.summerSolsticeOf(y);
            day = summer.getDayOfMonth();
            hour = summer.getHour();
            min = summer.getMinute();
            Console.print("| {0} | {1}:{2}", day, hour, min);

            LocalDateTime automn = Season.automnalEquinoxOf(y);
            day = automn.getDayOfMonth();
            hour = automn.getHour();
            min = automn.getMinute();
            Console.print("| {0} | {1}:{2}", day, hour, min);

            Console.println();
        }

    }

    private static void printRepublicanDates(Locale display) {
        Month month = Month.SEPTEMBER;

        for (int i=1; i<=month.length(false); i++) {
            printRepublicanDate(LocalDate.of(1793, month, i), display);
        }
    }

    private static void printRepublicanDate(LocalDate localDate, Locale display) {
        RepublicanDate republicanDate = RepublicanDate.of(localDate);
        Console.println(localDate + " -> " + republicanDate.getDisplayName(display));
    }

    private static void printRomanDates(Locale display) {
        printRomanDate(LocalDate.of(2025, Month.JANUARY, 16), display);

        Locale latin = new Locale("la");  // Locale.forLanguageTag("la");
//        /Locale latin = Language.LATIN.toLocale();
        DateFormatSymbols symbols = new DateFormatSymbols(latin);
        String[] weekDays = symbols.getWeekdays();
        Console.println(weekDays);


        for (int i=Month.JANUARY.getValue(); i<=Month.MARCH.getValue(); i++) {
            Month month = Month.of(i);
            for (int j=1; j <= month.length(false); j++) {
                //printRomanDate(LocalDate.of(2025, month, j));
            }
        }
    }

    private static void printRomanDate(LocalDate localDate, Locale display) {
        LocalDate julian = JulianCalendar.toJulianDate(localDate);
        RomanDate romanDate = RomanDate.ofGregorian(localDate);

        Console.println("{0} -> {1}", localDate, julian);
        Console.println("  {0} ", romanDate.getDisplayName(TextStyle.FULL));
        Console.println("  {0} ", romanDate.getDisplayName(TextStyle.FULL, display));
        Console.println("  {0} ", romanDate.getDisplayName(TextStyle.SHORT, display));
        Console.println();
    }
}
