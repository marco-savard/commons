package com.marcosavard.commons.ui.color;

import com.marcosavard.commons.math.RessourceEnum;
import com.marcosavard.commons.ui.res.ColorNameResource;
import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum WebSafeColor implements RessourceEnum {
  AMBER(0xF0C300, Origin.MINERAL),
  AZURE(0xF0FFFF),
  BEIGE(0xF5F5DC),
  BISQUE(0xFFE4C4, Origin.FOOD),
  BLACK(0x000000),
  BLUE(0x0000FF),
  BRONZE(0x614E1A, Origin.METAL),
  BROWN(0xA52A2A),
  CHARTREUSE(0x7FFF00, Origin.DRINK),
  CHOCOLATE(0xD2691E, Origin.FOOD),
  COPPER(0xB36700, Origin.METAL),
  CORAL(0xFF7F50, Origin.ANIMAL),
  CYAN(0x00FFFF),
  DARK_GRAY(GwtColor.DARK_GRAY.getRGB()),
  FLAVE(0xE6E697),
  FUCHSIA(0xFF00FF, Origin.VEGETAL),
  GLAUQUE(0x649B88),
  GOLD(0xFFD700, Origin.METAL),
  GRAY(0x808080),
  GREEN(0x008000),
  INDIGO(0x4B0082, Origin.VEGETAL),
  IVORY(0xFFFFF0, Origin.ANIMAL),
  KHAKI(0xF0E68C),
  LIGHT_GRAY(GwtColor.LIGHT_GRAY.getRGB()),
  LILAC(0xB666D2, Origin.FLORAL),
  LIME(0x00FF00, Origin.FRUIT),
  MAGENTA(0xFF00FF),
  MARROON(800000, Origin.FRUIT),
  MAUVE(0xD473D4, Origin.FLORAL),
  NAVY(0x000080),
  OCRE(0xDFAF2C, Origin.MINERAL),
  OLIVE(0x808000, Origin.FRUIT),
  ORANGE(0xFF8000, Origin.FRUIT),
  PERVENCHE(0xCCCCFF, Origin.FLORAL),
  PINK(0xFFC0CB),
  PISTACHE(0xBEF574, Origin.FRUIT),
  PLATINUM(0xE5E4E2, Origin.METAL),
  PLUM(0xDDA0DD, Origin.FRUIT),
  PURPLE(0x800080),
  RED(0xFF0000),
  RUBIS(0xE0115F, Origin.MINERAL),
  RUST(0x985717, Origin.METAL),
  SCARLET(0xED0000),
  SEPIA(0xAE8964),
  SILVER(0xC0C0C0, Origin.METAL),
  TEAL(0x008080),
  TURQUOISE(0x40E0D0, Origin.MINERAL),
  VERDET(0x85a585),
  VIRIDE(0x40826D, Origin.METAL),
  VIOLET(0xEE82EE, Origin.FLORAL),
  WHITE(0xFFFFFF),
  YELLOW(0xFFFF00);

  public static enum Origin {
    FLORAL,
    FRUIT,
    VEGETAL,
    ANIMAL,
    MINERAL,
    METAL,
    FOOD,
    DRINK
  }

  private static Map<Integer, String> keysByColor = null;

  private static Map<Integer, Currency> currencyByColor = null;

  private static ColorNameResource colorNameResource = new ColorNameResource();

  private final WebColor color;

  private final Origin origin;

  WebSafeColor(int rgb) {
    this(rgb, null);
  }

  WebSafeColor(int rgb, Origin origin) {
    this.color = WebColor.ofRGB(rgb);
    this.origin = origin;
  }

  public Origin getOrigin() {
    return origin;
  }

  @Override
  public String getDisplayName(Locale display) {
    String name = findColorNameByUiResource(display);
    name = (name != null) ? name : findMetalColor(display);
    name = (name != null) ? name : findColorResource(display);
    name = (name != null) ? name : RessourceEnum.super.getDisplayName(display);
    return name;
  }

  public static WebSafeColor findClosestColor(GwtColor thatColor) {
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
      keysByColor.put(GwtColor.RED.getRGB(), "ColorChooser.rgbRedText");
      keysByColor.put(WebSafeColor.GREEN.color.getRGB(), "ColorChooser.rgbGreenText");
      keysByColor.put(GwtColor.BLUE.getRGB(), "ColorChooser.rgbBlueText");
      keysByColor.put(GwtColor.YELLOW.getRGB(), "ColorChooser.cmykYellow.textAndMnemonic");
      keysByColor.put(GwtColor.CYAN.getRGB(), "ColorChooser.cmykCyan.textAndMnemonic");
      keysByColor.put(GwtColor.MAGENTA.getRGB(), "ColorChooser.cmykMagenta.textAndMnemonic");
      keysByColor.put(GwtColor.BLACK.getRGB(), "ColorChooser.cmykBlack.textAndMnemonic");
    }

    String colorName = null;
    String colorKey = keysByColor.get(color.getRGB());

    if (colorKey != null) {
      UIDefaults defaults = UIManagerFacade.getDefaults();
      colorName = defaults.getString(colorKey, display).toLowerCase();
    }

    return colorName;
  }



  public int getRGB() {
    return this.color.getRGB();
  }

  public int[] getRgbArray() {
    return getRgbArray(this.color);
  }

  public static int[] getRgbArray(GwtColor color) {
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    return new int[] {r, g, b};
  }

  public WebColor getColor() {
    return color;
  }
}
