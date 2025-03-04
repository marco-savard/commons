package com.marcosavard.commons.time.calendar;

import com.marcosavard.commons.debug.Console;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

public abstract class UniversalCalendar {

    public abstract List<Integer> getMonthLengths(int year);

    private List<Integer> cummulate(List<Integer> original) {
        Integer[] arr = original.toArray(Integer[]::new);
        Arrays.parallelPrefix(arr, Integer::sum);
        List<Integer> cummulative = Arrays.asList(arr);
        return cummulative;
    }

    public int getMonthOfYear(int year, int dayOfYear) {
        List<Integer> monthLengths = getMonthLengths(year);
        List<Integer> cummulative = cummulate(monthLengths);
        int n = cummulative.stream().filter(d -> dayOfYear <= d).findFirst().orElse(0);
        int month = cummulative.indexOf(n);
        return month;
    }

    public int getDayOfMonth(int year, int dayOfYear) {
        List<Integer> monthLengths = getMonthLengths(year);
        List<Integer> cummulative = cummulate(monthLengths);
        int month = getMonthOfYear(year, dayOfYear);
        int dayOfMonth = (month == 0) ? dayOfYear : dayOfYear - cummulative.get(month - 1);
        return dayOfMonth;
    }

    public static class GregorianCalendar extends UniversalCalendar {
        public List<Integer> getMonthLengths(int year) {
            Month[] months = Month.values();
            List<Integer> monthLengths = Arrays.stream(months).sequential().map(m -> m.length(Year.isLeap(year))).toList();
            return monthLengths;
        }
    }

    public static void main(String[] args) {
        UniversalCalendar calendar = new GregorianCalendar();
        int year = 2025;

        for (int i=1; i<=365; i++) {
            int dayOfYear = i;
            int monthOfYear = calendar.getMonthOfYear(year, dayOfYear);
            int dayOfMonth = calendar.getDayOfMonth(year, dayOfYear);
            Console.println("{0} -> {1}/{2}", dayOfYear, monthOfYear+1, dayOfMonth);
        }

    }

}
