package com.marcosavard.common.text;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ComparatorUtilDemo {
    private static List<String> strings = Arrays.asList(new String[] {
            "Zèbre1.jpg", "lion9.jpg", "lion10.jpg", "Éléphant.jpg", "girafe.jpg"}
    );

    public static void main(String[] args) {
        demoCompareStrings();
        demoCompareDates();
    }

    private static void demoCompareStrings() {
        System.out.println("For strings = [" + String.join(", ",  strings) + "]");

        Collections.sort(strings);
        display("Collections.sort(list)", strings);
        display("Collections.sort(list, Comparator.naturalOrder())", strings, Comparator.naturalOrder());
        display("Collections.sort(list, ComparatorUtil.naturalOrder()", strings, ComparatorUtil.naturalOrder());
        display("Collections.sort(list, ComparatorUtil.ignoreCaseOrder()", strings, ComparatorUtil.ignoreCaseOrder());
        display("Collections.sort(list, ComparatorUtil.ignoreAccentOrder()", strings, ComparatorUtil.ignoreAccentOrder());
        display("Collections.sort(list, ComparatorUtil.ignoreCaseAccentOrder()", strings, ComparatorUtil.ignoreCaseAccentOrder());
        display("Collections.sort(list, ComparatorUtil.collatorOrder(Locale.FRENCH)", strings, ComparatorUtil.collatorOrder(Locale.FRENCH));
        display("Collections.sort(list, ComparatorUtil.stringLengthComparator()", strings, ComparatorUtil.stringLengthComparator());

    }

    private static void demoCompareDates() {
        String pattern = "d MMMM yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.FRENCH);

        List<LocalDateTime> dates = generateRandomLocalDateTime(6);
        List<String> strings = new ArrayList<>();
        strings.addAll(dates.stream().map(d -> d.format(formatter)).toList());

        System.out.println("For dates = [" + String.join(", ",  strings) + "]");
        display("Collections.sort(list, ComparatorUtil.dateTimeComparator(\"dd MMMM yyyy\", Locale.FRENCH)", strings, ComparatorUtil.dateTimeComparator(pattern, Locale.FRENCH));
        System.out.println();
    }

    private static List<LocalDateTime> generateRandomLocalDateTime(int count) {
        // Define a range: 50 years ago to now
        LocalDateTime start = LocalDateTime.now().minusYears(50);
        LocalDateTime end = LocalDateTime.now();
        List<LocalDateTime> randomDates = new ArrayList<>();

        long startEpoch = start.toEpochSecond(ZoneOffset.UTC);
        long endEpoch = end.toEpochSecond(ZoneOffset.UTC);

        for (int i = 0; i < count; i++) {
            long randomEpoch = ThreadLocalRandom.current().nextLong(startEpoch, endEpoch);
            LocalDateTime randomDate = LocalDateTime.ofEpochSecond(randomEpoch, 0, ZoneOffset.UTC);
            randomDates.add(randomDate);
        }

        return randomDates;
    }

    private static void display(String title, List<String> list) {
        display(title, list, null);
    }

    private static void display(String title, List<String> list, Comparator<String> comparator) {
        if (comparator == null) {
            Collections.sort(list);
        } else {
            Collections.sort(list, comparator);
        }

        System.out.println("  " + title + " : " + String.join(", ",  list));
    }



}
