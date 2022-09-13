package com.marcosavard.commons.ui.i18n;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.UIDefaults;
import java.util.Locale;

public class YesNoDemo {

    public static void main(String[] args) {
        Locale locale = Locale.FRENCH;
        UIDefaults uiDefaults = UIManagerFacade.getDefaults();
        Console.println(uiDefaults.getString("OptionPane.yesButtonText", locale));
        Console.println(uiDefaults.getString("OptionPane.noButtonText", locale));
    }

}
