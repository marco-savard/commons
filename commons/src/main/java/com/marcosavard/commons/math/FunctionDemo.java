package com.marcosavard.commons.math;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;

public class FunctionDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;

        for (Function f : Function.values()) {
            Console.println(f.getDisplayName(display));
        }
    }
}
