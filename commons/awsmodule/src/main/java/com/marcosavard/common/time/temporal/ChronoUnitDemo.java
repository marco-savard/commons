package com.marcosavard.common.time.temporal;

import java.text.MessageFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChronoUnitDemo {

    public static void main(String[] args) {
        List<String> displayNames = new ArrayList<>();

        //list chrono units in standard Java
        for (ChronoUnit chronoUnit : ChronoUnit.values()) {
            displayNames.add(chronoUnit.name().toLowerCase());
        }

        System.out.println("ChronoUnit.name().toLowerCase(): [" + String.join(", ", displayNames) + "]");

        displayChronoUnits(Locale.ENGLISH);
        displayChronoUnits(Locale.FRENCH);
    }

    private static void displayChronoUnits(Locale display) {
        List<String> displayNames = new ArrayList<>();

        //list chrono units in extended Java
        for (ChronoUnit chronoUnit : ChronoUnit.values()) {
            ChronoInfo info = ChronoInfo.of(chronoUnit);
            String name = info.getDisplayName(display);
            displayNames.add(name);
        }

        String lang = display.getDisplayLanguage(Locale.ENGLISH).toUpperCase();
        String joined = String.join(", ", displayNames);
        String pattern = "ChronoInfo.of(chronoUnit).getDisplayName({0}): [{1}])";
        System.out.println(MessageFormat.format(pattern, lang, joined));
    }

}
