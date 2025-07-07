package com.marcosavard.common.text;

import java.text.Collator;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class ComparatorUtil {
    private static Comparator<String> naturalOrder = new NaturalOrderComparator();
    private static Comparator<String> ignoreCaseOrder = new IgnoreCaseComparator();
    private static Comparator<String> ignoreAccentOrder = new IgnoreAccentComparator(false);
    private static Comparator<String> ignoreCaseAccentOrder = new IgnoreAccentComparator(true);
    private static Comparator<String> stringLengthComparator = new StringLengthComparator();
    private static Map<Locale, Comparator<String>> comparatorByLocale = new HashMap<>();
    private static Map<LocalizedFormat, Comparator<String>> comparatorByLocalizedFormat = new HashMap<>();

    public static Comparator<String> naturalOrder() {
        return naturalOrder;
    }

    public static Comparator<String> ignoreCaseOrder() {
        return ignoreCaseOrder;
    }

    public static Comparator<String> ignoreAccentOrder() {
        return ignoreAccentOrder;
    }

    public static Comparator<String> ignoreCaseAccentOrder() {
        return ignoreCaseAccentOrder;
    }

    public static Comparator<String> stringLengthComparator() {
        return stringLengthComparator;
    }

    public static Comparator<String> collatorOrder(Locale locale) {
        Comparator<String> comparator = comparatorByLocale.get(locale);

        if (comparator == null) {
            comparator = new CollatorComparator(locale);
            comparatorByLocale.put(locale, comparator);
        }

        return comparator;
    }

    public static Comparator<String> dateTimeComparator(String format, Locale locale) {
        LocalizedFormat localizedFormat = new LocalizedFormat(format, locale);
        Comparator<String> comparator = comparatorByLocalizedFormat.get(localizedFormat);

        if (comparator == null) {
            comparator = new DateTimeComparator(format, locale);
            comparatorByLocale.put(locale, comparator);
        }

        return comparator;
    }

    public record LocalizedFormat(String format, Locale locale) {}

    public static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int comparison = Integer.compare(s1.length(), s2.length());
            return (comparison == 0) ? ignoreCaseOrder().compare(s1, s2) : comparison;
        }
    }

    private static class IgnoreCaseComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }
    }

    private static class IgnoreAccentComparator implements Comparator<String> {
        private static final Pattern DIACRITICS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        private boolean ignoreCase;

        IgnoreAccentComparator(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        @Override
        public int compare(String s1, String s2) {
            String norm1 = normalize(s1);
            String norm2 = normalize(s2);
            return ignoreCase ?  norm1.compareToIgnoreCase(norm2) : norm1.compareTo(norm2);
        }

        private String normalize(String input) {
            if (input == null) return "";
            // Normalize to NFD (decomposed form), remove diacritics, convert to lowercase
            String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
            String noDiacritics = DIACRITICS_PATTERN.matcher(normalized).replaceAll("");
            return noDiacritics;
        }
    }

    private static class NaturalOrderComparator implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            int i = 0, j = 0;
            int len1 = s1.length(), len2 = s2.length();

            while (i < len1 && j < len2) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(j);

                // If both characters are digits
                if (Character.isDigit(c1) && Character.isDigit(c2)) {
                    // Get the full number from both strings
                    int start1 = i;
                    while (i < len1 && Character.isDigit(s1.charAt(i))) i++;
                    String num1 = s1.substring(start1, i);

                    int start2 = j;
                    while (j < len2 && Character.isDigit(s2.charAt(j))) j++;
                    String num2 = s2.substring(start2, j);

                    // Compare as integer values
                    int cmp = Integer.compare(Integer.parseInt(num1), Integer.parseInt(num2));
                    if (cmp != 0) {
                        return cmp;
                    }
                } else {
                    // Compare characters (case-sensitive)
                    if (c1 != c2) {
                        return Character.compare(c1, c2);
                    }
                    i++;
                    j++;
                }
            }
            return Integer.compare(len1, len2);
        }
    }


    private static class CollatorComparator implements Comparator<String> {
        private Collator collator;

        public CollatorComparator(Locale locale) {
            collator = Collator.getInstance(locale);
        }

        @Override
        public int compare(String s1, String s2) {
            return collator.compare(s1, s2);
        }
    }

    private static class DateTimeComparator implements Comparator<String> {
        private static final ZoneId zoneId = ZoneId.systemDefault();
        private final DateTimeFormatter dateTimeFormatter;

        public DateTimeComparator(String pattern, Locale locale) {
            this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, locale);
        }

        @Override
        public int compare(String s1, String s2) {
            long d1 = LocalDate.parse(s1, dateTimeFormatter).atStartOfDay().atZone(zoneId).toEpochSecond();
            long d2 = LocalDate.parse(s2, dateTimeFormatter).atStartOfDay().atZone(zoneId).toEpochSecond();
            return (int)(d1 - d2);
        }
    }


}
