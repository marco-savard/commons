package com.marcosavard.common.ui;

import java.awt.Color;
import java.util.Locale;

public class ColorInfoDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;
        System.out.println(ColorInfo.of(Color.RED).getDisplayName(display));
        Color[] colors = ColorInfo.getConstantColors();

        for (Color color : colors) {
            ColorInfo info = ColorInfo.of(color);
            System.out.println(info.getFieldName() + " : " + info.getDisplayName(display));
        }
    }
}
