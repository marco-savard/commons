package com.marcosavard.commons.ui.text;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Locale;

public class ColorNameDemo {
    private static final String RED = "ColorChooser.rgbRedText";
    private static final String GREEN = "ColorChooser.rgbGreenText";
    private static final String BLUE = "ColorChooser.rgbBlueText";


    public static void main(String[] args) {
        Locale locale = Locale.FRENCH;
        UIDefaults uiDefaults = UIManagerFacade.getDefaults();

        Console.println(uiDefaults.getString("OptionPane.yesButtonText", locale));
        Console.println(uiDefaults.getString("OptionPane.noButtonText", locale));

        Console.println(uiDefaults.getString(RED, locale));
        Console.println(uiDefaults.getString(GREEN, locale));

        Console.println("Succes");
    }
}
