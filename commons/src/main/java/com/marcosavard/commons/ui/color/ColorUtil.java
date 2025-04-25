package com.marcosavard.commons.ui.color;

import com.marcosavard.commons.ui.res.ColorNameResource;
import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.UIDefaults;
import java.awt.Color;
import java.util.*;

public class ColorUtil {
    private static Map<Color, String> uiDefaultByColor = null;
    private static Map<Color, String> resourceByColor = null;
    private static Map<Color, Currency> currencyByColor = null;
    private static ColorNameResource colorNameResource = new ColorNameResource();

    private enum DefinedColor {
        BLACK(Color.BLACK),
        DARK_GRAY(Color.DARK_GRAY),
        GRAY(Color.GRAY),
        LIGHT_GRAY(Color.LIGHT_GRAY),
        WHITE(Color.WHITE),
        RED(Color.RED),
        PINK(Color.PINK),
        ORANGE(Color.ORANGE),
        YELLOW(Color.YELLOW),
        CYAN(Color.CYAN),
        GREEN(Color.GREEN),
        BLUE(Color.BLUE),
        MAGENTA(Color.MAGENTA);

        private final Color color;
        private final int rgb;

        DefinedColor(Color color) {
            this.color = color;
            this.rgb = color.getRGB();
        }

        public Color getColor() {
            return color;
        }
    }

    private enum CurrencyColor {
        GOLD(0xFFD700, Currency.getInstance("XAU")),
        SILVER(0xC4C4C4, Currency.getInstance("XAG")),
        PLATINUM(0xE5E4E2, Currency.getInstance("XPT"));

        private final Color color;
        private final int rgb;
        private final Currency currency;

        CurrencyColor(int rgb, Currency currency) {
            this.color = new Color(rgb);
            this.rgb = rgb;
            this.currency = currency;
        }

        public Color getColor() {
            return color;
        }
    }

    public static Color[] getDefinedColors() {
        return Arrays.stream(DefinedColor.values()).map(e -> e.getColor()).toList().toArray(new Color[0]);
    }

    public static Color[] getCurrencyColors() {
        return Arrays.stream(CurrencyColor.values()).map(e -> e.getColor()).toList().toArray(new Color[0]);
    }

    public static String getDisplayName(Color color, Locale locale) {
        init();
        String colorName = null;
        String key = uiDefaultByColor.get(color);

        if (key != null) {
            UIDefaults defaults = UIManagerFacade.getDefaults();
            colorName = defaults.getString(key, locale).toLowerCase(locale);
        } else if (currencyByColor.containsKey(color)) {
            Currency currency = currencyByColor.get(color);
            colorName = currency.getDisplayName(locale);
        } else {
            key = resourceByColor.get(color);
            colorName = colorNameResource.getColorName(key, locale);
        }

        return colorName;
    }

    private static void init() {
        if (uiDefaultByColor == null) {
            uiDefaultByColor = new HashMap<>();
            uiDefaultByColor.put(Color.RED, "ColorChooser.rgbRedText");
            uiDefaultByColor.put(Color.GREEN, "ColorChooser.rgbGreenText");
            uiDefaultByColor.put(Color.BLUE, "ColorChooser.rgbBlueText");
            uiDefaultByColor.put(Color.YELLOW, "ColorChooser.cmykYellow.textAndMnemonic");
            uiDefaultByColor.put(Color.CYAN, "ColorChooser.cmykCyan.textAndMnemonic");
            uiDefaultByColor.put(Color.MAGENTA, "ColorChooser.cmykMagenta.textAndMnemonic");
            uiDefaultByColor.put(Color.BLACK, "ColorChooser.cmykBlack.textAndMnemonic");
        }

        if (resourceByColor == null) {
            resourceByColor = new HashMap<>();
            resourceByColor.put(Color.DARK_GRAY, "dark_gray");
            resourceByColor.put(Color.GRAY, "gray");
            resourceByColor.put(Color.LIGHT_GRAY, "light_gray");
            resourceByColor.put(Color.ORANGE, "orange");
            resourceByColor.put(Color.PINK, "pink");
            resourceByColor.put(Color.WHITE, "white");
        }

        if (currencyByColor == null) {
            currencyByColor = new HashMap<>();
            currencyByColor.put(CurrencyColor.GOLD.getColor(), Currency.getInstance("XAU"));
            currencyByColor.put(CurrencyColor.SILVER.getColor(), Currency.getInstance("XAG"));
            currencyByColor.put(CurrencyColor.PLATINUM.getColor(), Currency.getInstance("XPT"));
        }
    }


}
