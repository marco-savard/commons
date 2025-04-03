package com.marcosavard.commons.math;

import com.marcosavard.commons.debug.Console;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class IntegerListDemo {

    public static void main(String[] args) {
        demoIntegerList();
    }

    private static void demoIntegerList() {
        Month[] months = Month.values();
        List<Integer> monthLengths = Arrays.stream(months).sequential().map(m -> m.length(true)).toList();

        List<Integer> cummulative = IntegerList.cummulate(monthLengths);
        System.out.println(monthLengths);
        System.out.println(cummulative);

        for (int i=1; i<=366; i++) {
            int dayOfYear = i;
            int n = cummulative.stream().filter(d -> dayOfYear <= d).findFirst().orElse(0);
            int month = cummulative.indexOf(n);
            int dayOfMonth = (month == 0) ? dayOfYear : dayOfYear - cummulative.get(month - 1);
            Console.println("{0} -> {1}/{2}", dayOfYear, month+1, dayOfMonth);
        }
    }
}
