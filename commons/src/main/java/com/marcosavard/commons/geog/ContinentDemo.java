package com.marcosavard.commons.geog;

import com.marcosavard.commons.text.DisplayText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ContinentDemo {
    public static void main(String[] args) {
        Locale fr = Locale.FRENCH;
        Continent[] continents = Continent.values();
        
        for (Continent continent : continents) {
            displayContinent(fr, continent);
        }
    }

    private static void displayContinent(Locale displayLocale, Continent continent) {
        TimeZone[] timeZones = continent.getTimeZones();

        System.out.println(continent.getDisplayName(displayLocale));
        System.out.println("  " + toString(timeZones));
        System.out.println();
    }

    private static String toString(TimeZone[] timeZones) {
        List<String> names = new ArrayList<>();

        for (TimeZone timeZone : timeZones) {
            String id = timeZone.getID();
            int idx = id.indexOf('/');
            String name = id.substring(idx+1);
            names.add(name);
        }

        String str = "[" + String.join(", ", names) + "]";
        return str;
    }

}
