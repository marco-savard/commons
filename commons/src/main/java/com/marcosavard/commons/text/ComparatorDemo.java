package com.marcosavard.commons.text;

import com.marcosavard.commons.lang.NullSafe;
import com.marcosavard.commons.lang.StringUtil;

import java.util.*;

public class ComparatorDemo {
    private static String[] strings = new String[] {
            "éléphant", "girafe", "Lion", "zèbre", null
    };

    public static void main(String[] args) {
        List<String> list = Arrays.asList(strings);
        Comparator<String> ignoreCaseComparator = new IgnoreCaseComparator();
        Comparator<String> ignoreAccentComparator = new IgnoreAccentComparator();
        Comparator<String> ignoreCaseAccentComparator = new IgnoreCaseAccentComparator();

        sortAndPrint(list, "LexicographicalComparator");
        sortAndPrint(list, "IgnoreCaseComparator", ignoreCaseComparator);
        sortAndPrint(list, "IgnoreAccentComparator", ignoreAccentComparator);
        sortAndPrint(list, "IgnoreCaseAccentComparator", ignoreCaseAccentComparator);
    }

    private static void sortAndPrint(List<String> list, String sortName) {
        sortAndPrint(list, sortName, null);
    }

    private static void sortAndPrint(List<String> list, String sortName, Comparator<String> comparator) {
        List<String> nullsafeList = (List<String>) NullSafe.of(list);
        Collections.sort(nullsafeList, comparator);
        System.out.println(sortName + " : " + String.join(", ", StringUtil.stripAccents(list)));
    }

    private static class IgnoreCaseComparator implements Comparator<String> {

        @Override
        public int compare(String first, String second) {
            return first.compareToIgnoreCase(second);
        }

        @Override
        public boolean equals(Object that) {
            return (that instanceof IgnoreCaseComparator);
        }
    }

    private static class IgnoreAccentComparator implements Comparator<String> {

        @Override
        public int compare(String first, String second) {
            return StringUtil.compareToIgnoreAccent(first, second);
        }

        @Override
        public boolean equals(Object that) {
            return (that instanceof IgnoreAccentComparator);
        }
    }

    private static class IgnoreCaseAccentComparator implements Comparator<String> {

        @Override
        public int compare(String first, String second) {
            return StringUtil.compareToIgnoreCaseAccent(first, second);
        }

        @Override
        public boolean equals(Object that) {
            return (that instanceof IgnoreCaseAccentComparator);
        }
    }
}
