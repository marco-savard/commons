package com.marcosavard.commons.ui.color;

import com.marcosavard.commons.ui.res.ColorNameResource;
import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ColorName {

  private static Map<GwtColor, ColorName> namesByColor = new HashMap<>();
  private static Map<GwtColor, String> keysByColor = null;
  private static Map<GwtColor, String> enNamesByColor = null;
  private GwtColor color;

  public static List<GwtColor> getNamedColors() {
    return List.of(
        GwtColor.RED,
        GwtColor.GREEN,
        GwtColor.BLUE,
        GwtColor.YELLOW,
        GwtColor.CYAN,
        GwtColor.MAGENTA, //
        GwtColor.BLACK,
        GwtColor.WHITE, //
        GwtColor.GRAY, //
        GwtColor.LIGHT_GRAY, //
        GwtColor.DARK_GRAY, //
        GwtColor.ORANGE,
        GwtColor.GRAY,
        GwtColor.PINK);
  }

  public static ColorName of(GwtColor color) {
    ColorName colorName = namesByColor.get(color);

    if (!namesByColor.containsKey(color)) {
      colorName = new ColorName(color);
      namesByColor.put(color, colorName);
    }

    return colorName;
  }

  private ColorName(GwtColor color) {
    this.color = color;
  }

  @Override
  public String toString() {
    return toString(Locale.getDefault());
  }

  public String toString(Locale locale) {
    String colorName = findColorNameByUiResource(color, locale);

    if (colorName == null) {
      ColorNameResource colorNameResource = new ColorNameResource();
      String colorNameEn = findColorNameEnByIntrospection(color);
      colorName = colorNameResource.getColorName(colorNameEn, locale);
    }

    return colorName;
  }

  private static String findColorNameByUiResource(GwtColor color, Locale locale) {
    if (keysByColor == null) {
      keysByColor = new HashMap<>();
      keysByColor.put(GwtColor.RED, "ColorChooser.rgbRedText");
      keysByColor.put(GwtColor.GREEN, "ColorChooser.rgbGreenText");
      keysByColor.put(GwtColor.BLUE, "ColorChooser.rgbBlueText");
      keysByColor.put(GwtColor.YELLOW, "ColorChooser.cmykYellow.textAndMnemonic");
      keysByColor.put(GwtColor.CYAN, "ColorChooser.cmykCyan.textAndMnemonic");
      keysByColor.put(GwtColor.MAGENTA, "ColorChooser.cmykMagenta.textAndMnemonic");
      keysByColor.put(GwtColor.BLACK, "ColorChooser.cmykBlack.textAndMnemonic");
    }

    String colorName = null;
    String colorKey = keysByColor.get(color);
    if (colorKey != null) {
      UIDefaults defaults = UIManagerFacade.getDefaults();
      colorName = defaults.getString(colorKey, locale);
    }

    return colorName;
  }

  private static String findColorNameEnByIntrospection(GwtColor color) {
    if (enNamesByColor == null) {
      enNamesByColor = new HashMap<>();
      Field[] fields = GwtColor.class.getDeclaredFields();
      for (Field field : fields) {
        if (Modifier.isStatic(field.getModifiers())) {
          try {
            String fieldName = field.getName();
            String name = canonize(fieldName);
            Object value = field.get(null);

            if ((name != null) && (value instanceof GwtColor)) {
              enNamesByColor.put((GwtColor) value, name);
            }

          } catch (IllegalArgumentException | IllegalAccessException e) {
            // skip
          }
        }
      }
    }

    String colorName = enNamesByColor.get(color);
    return colorName;
  }

  private static String canonize(String fieldName) {
    boolean uppercase = fieldName.endsWith(fieldName.toUpperCase());
    String canonized = null;

    if (uppercase) {
      fieldName = fieldName.replace('_', ' ');
      canonized = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1).toLowerCase();
    }

    return canonized;
  }
}
