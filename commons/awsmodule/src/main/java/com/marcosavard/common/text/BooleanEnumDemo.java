package com.marcosavard.common.text;

import com.marcosavard.common.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public class BooleanEnumDemo {
    private static final Locale display = Locale.FRENCH;

    public static void main(String[] args) {
        System.out.println("Standard Java :");
        System.out.println("  " + Boolean.TRUE.toString());
        System.out.println("  " + Boolean.FALSE.toString());
        System.out.println();

        System.out.println("BooleanEnum.toString() :");
        System.out.println("  " + BooleanEnum.TRUE.toString(display));
        System.out.println("  " + BooleanEnum.FALSE.toString(display));
        System.out.println();

        System.out.println("BooleanEnum.getDisplayName() :");
        System.out.println("  " + BooleanEnum.valueOf(true).getDisplayName(display));
        System.out.println("  " + BooleanEnum.valueOf(false).getDisplayName(display));
        System.out.println();

        System.out.println("BooleanEnum.getDisplayName(BooleanEnum.Style) :");
        System.out.println("  " + BooleanEnum.valueOf(true).getDisplayName(display, BooleanEnum.Style.TRUE_FALSE));
        System.out.println("  " + BooleanEnum.valueOf(true).getDisplayName(display, BooleanEnum.Style.YES_NO));
        System.out.println();
    }
}
