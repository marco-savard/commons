package com.marcosavard.commons.geog;

import com.marcosavard.commons.text.DisplayText;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class ContinentDemo {
    public static void main(String[] args) {
        Continent[] continents = Continent.values();
        
        for (Continent continent : continents) {
            displayContinent(continent); 
        }
    }

    private static void displayContinent(Continent continent) {
        TimeZone[] timeZones = continent.getTimeZones();

        System.out.println(continent);
        System.out.println("  " + toString(timeZones));
        System.out.println();
    }

    private static String toString(TimeZone[] timeZones) {
        List<String> names = new ArrayList<>();

        for (TimeZone timeZone : timeZones) {
            names.add(timeZone.getID());
        }

        String str = "[" + String.join(", ", names) + "]";
        return str;
    }

}
