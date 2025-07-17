package com.marcosavard.common.ui;

import com.marcosavard.common.ling.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActionDemo {

    public static void main(String[] args) {
        List<Locale> displays = List.of(Locale.ENGLISH, Locale.FRENCH, Locale.ITALIAN, Language.SPANISH.toLocale());

        for (Locale display : displays) {
            List<String> actions = getActionDisplayNames(display);
            System.out.println(display.getDisplayLanguage(display) + " : " + String.join(", ", actions));
        }
    }

    private static List<String> getActionDisplayNames(Locale display) {
        List<String> actions = new ArrayList<>();
        for (Action action : Action.values()) {
            actions.add(action.getDisplayName(display));
        }

        return actions;
    }

}
