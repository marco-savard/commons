package com.marcosavard.common.text;

import com.marcosavard.common.ui.res.UIManagerFacade;
import com.marcosavard.common.util.RessourceEnum;

import javax.swing.*;
import java.util.Locale;

public enum BooleanEnum implements RessourceEnum {
    FALSE,
    TRUE;

    private static UIDefaults uiDefaults = UIManagerFacade.getDefaults();

    public enum Style {
        TRUE_FALSE,
        YES_NO;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public String toString(Locale display) {
        return getDisplayName(display);
    }

    public String getDisplayName(Locale display, Style style) {
        if (style == Style.YES_NO) {
            String key = (this == BooleanEnum.TRUE) ? "OptionPane.yesButtonText" : "OptionPane.noButtonText";
            return uiDefaults.getString(key, display).toLowerCase(display);
        } else {
            return getDisplayName(display);
        }
    }

    public static BooleanEnum valueOf(boolean bool) {
        return valueOf(Boolean.valueOf(bool).toString().toUpperCase());
    }
}
