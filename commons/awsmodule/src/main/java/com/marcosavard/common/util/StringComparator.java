package com.marcosavard.common.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//TODO chrono-order
//nom, prenom
//address
//title
public class StringComparator {
    private static Map<Locale, Comparator<String>> comparatorByLocal = new HashMap<>();

    public static Comparator<? super String> naturalOrder() {
        return Comparator.naturalOrder();
    }

    public static Comparator<? super String> collatorOrder(Locale locale) {
        Comparator<String> comparator = comparatorByLocal.get(locale);

        if (comparator == null) {
            comparator = new CollatorComparator(locale);
            comparatorByLocal.put(locale, comparator);
        }

        return comparator;
    }

    public static Comparator<? super String> shortlex() {
        //TODO sort by length, then lexicographically
        return Comparator.naturalOrder();
    }




    private static class CollatorComparator implements Comparator<String> {
        Collator collator;

        public CollatorComparator(Locale locale) {
            collator = Collator.getInstance(locale);
        }

        @Override
        public int compare(String s1, String s2) {
            return collator.compare(s1, s2);
        }
    }
}
