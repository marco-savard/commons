package com.marcosavard.commons.ui.color;

import com.marcosavard.commons.debug.Console;

import java.awt.*;
import java.util.Locale;

public class ColorUtilDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;

        for (Color color : ColorUtil.getDefinedColors()) {
            Console.println(ColorUtil.getDisplayName(color, display));
        }

        Console.println();
        for (Color color : ColorUtil.getCurrencyColors()) {
           Console.println(ColorUtil.getDisplayName(color, display));
        }
    }
}
