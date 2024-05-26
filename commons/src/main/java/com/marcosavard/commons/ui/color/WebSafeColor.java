package com.marcosavard.commons.ui.color;

import com.marcosavard.commons.ui.res.ColorNameResource;
import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.awt.*;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum WebSafeColor {
  AMBER(0xF0C300),
  AZURE(0xF0FFFF),
  BEIGE(0xF5F5DC),
  BISQUE(0xFFE4C4),
  BLACK(0x000000),
  BLUE(0x0000FF),
  BRONZE(0x614E1A),
  BROWN(0xA52A2A),
  CHARTREUSE(0x7FFF00),
  CHOCOLATE(0xD2691E),
  COPPER(0xB36700),
  CORAL(0xFF7F50),
  CYAN(0x00FFFF),
  DARK_GRAY(Color.DARK_GRAY.getRGB()),
  FLAVE(0xE6E697),
  FUCHSIA(0xFF00FF),
  GLAUQUE(0x649B88),
  GOLD(0xFFD700),
  GRAY(0x808080),
  GREEN(0x008000),
  INDIGO(0x4B0082),
  IVORY(0xFFFFF0),
  KHAKI(0xF0E68C),
  LIGHT_GRAY(Color.LIGHT_GRAY.getRGB()),
  LILAC(0xB666D2),
  LIME(0x00FF00),
  MAGENTA(0xFF00FF),
  MARROON(800000),
  MAUVE(0xD473D4),
  NAVY(0x000080),
  OCRE(0xDFAF2C),
  OLIVE(0x808000),
  ORANGE(0xFF8000),
  PERVENCHE(0xCCCCFF),
  PINK(0xFFC0CB),

  PISTACHE(0xBEF574),
  PLATINUM(0xE5E4E2),
  PLUM(0xDDA0DD),
  PURPLE(0x800080),
  RED(0xFF0000),
  RUBIS(0xE0115F),
  RUST(0x985717),
  SCARLET(0xED0000),
  SEPIA(0xAE8964),
  SILVER(0xC0C0C0),
  TEAL(0x008080),
  TURQUOISE(0x40E0D0),
  VERDET(0x85a585),
  VIRIDE(0x40826D),
  VIOLET(0xEE82EE),
  WHITE(0xFFFFFF),
  YELLOW(0xFFFF00);

  private static Map<Integer, String> keysByColor = null;

  private static Map<Integer, Currency> currencyByColor = null;

  private static ColorNameResource colorNameResource = new ColorNameResource();

  private final WebColor color;

  WebSafeColor(int rgb) {
    color = WebColor.ofRGB(rgb);
  }

  public static WebSafeColor findClosestColor(Color thatColor) {
    int closestDistance = Integer.MAX_VALUE;
    WebSafeColor closestColor = null;

    for (WebSafeColor c : WebSafeColor.values()) {
      int distance = c.getColor().distanceFrom(thatColor);

      if (distance < closestDistance) {
        closestDistance = distance;
        closestColor = c;
      }
    }

    return closestColor;
  }

  public String getDisplayName(Locale display) {
    String name = findColorNameByUiResource(display);
    name = (name != null) ? name : findMetalColor(display);
    name = (name != null) ? name : findColorResource(display);
    name = (name != null) ? name : name().toLowerCase();
    return name;
  }

  private String findColorResource(Locale display) {
    return colorNameResource.getColorName(name().toLowerCase(), display);
  }

  private String findMetalColor(Locale display) {
    if (currencyByColor == null) {
      currencyByColor = new HashMap<>();
      currencyByColor.put(WebSafeColor.GOLD.color.getRGB(), Currency.getInstance("XAU"));
      currencyByColor.put(WebSafeColor.SILVER.color.getRGB(), Currency.getInstance("XAG"));
      currencyByColor.put(WebSafeColor.PLATINUM.color.getRGB(), Currency.getInstance("XPT"));
    }

    String colorName = null;
    Currency currency = currencyByColor.get(color.getRGB());

    if (currency != null) {
      colorName = currency.getDisplayName(display);
    }

    return colorName;
  }

  //
  private String findColorNameByUiResource(Locale display) {
    if (keysByColor == null) {
      keysByColor = new HashMap<>();
      keysByColor.put(Color.RED.getRGB(), "ColorChooser.rgbRedText");
      keysByColor.put(WebSafeColor.GREEN.color.getRGB(), "ColorChooser.rgbGreenText");
      keysByColor.put(Color.BLUE.getRGB(), "ColorChooser.rgbBlueText");
      keysByColor.put(Color.YELLOW.getRGB(), "ColorChooser.cmykYellow.textAndMnemonic");
      keysByColor.put(Color.CYAN.getRGB(), "ColorChooser.cmykCyan.textAndMnemonic");
      keysByColor.put(Color.MAGENTA.getRGB(), "ColorChooser.cmykMagenta.textAndMnemonic");
      keysByColor.put(Color.BLACK.getRGB(), "ColorChooser.cmykBlack.textAndMnemonic");
    }

    String colorName = null;
    String colorKey = keysByColor.get(color.getRGB());

    if (colorKey != null) {
      UIDefaults defaults = UIManagerFacade.getDefaults();
      colorName = defaults.getString(colorKey, display).toLowerCase();
    }

    return colorName;
  }

  private double findAverageAngle(double a1, double a2) {
    double x1 = Math.cos(Math.toRadians(a1));
    double x2 = Math.cos(Math.toRadians(a2));
    double y1 = Math.sin(Math.toRadians(a1));
    double y2 = Math.sin(Math.toRadians(a2));
    double x = (x1 + x2) / 2;
    double y = (y1 + y2) / 2;
    return Math.toDegrees(Math.atan2(y, x));
  }

  public int getRGB() {
    return this.color.getRGB();
  }

  public int[] getRgbArray() {
    return getRgbArray(this.color);
  }

  public static int[] getRgbArray(Color color) {
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    return new int[] {r, g, b};
  }

  public WebColor getColor() {
    return color;
  }
}
