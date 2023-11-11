package com.marcosavard.commons.ui.color;

import com.marcosavard.commons.ui.res.ColorNameResource;
import com.marcosavard.commons.ui.res.UIManagerFacade;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ColorName {

  private static Map<Color, ColorName> namesByColor = new HashMap<>();
  private static Map<Color, String> keysByColor = null;
  private static Map<Color, String> enNamesByColor = null;
  private Color color;

  public static List<Color> getNamedColors() {
    return List.of(
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
        Color.CYAN,
        Color.MAGENTA, //
        Color.BLACK,
        Color.WHITE, //
        Color.GRAY, //
        Color.LIGHT_GRAY, //
        Color.DARK_GRAY, //
        Color.ORANGE,
        Color.GRAY,
        Color.PINK);
  }

  public static ColorName of(Color color) {
    ColorName colorName = namesByColor.get(color);

    if (!namesByColor.containsKey(color)) {
      colorName = new ColorName(color);
      namesByColor.put(color, colorName);
    }

    return colorName;
  }

  private ColorName(Color color) {
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

  private static String findColorNameByUiResource(Color color, Locale locale) {
    if (keysByColor == null) {
      keysByColor = new HashMap<>();
      keysByColor.put(Color.RED, "ColorChooser.rgbRedText");
      keysByColor.put(Color.GREEN, "ColorChooser.rgbGreenText");
      keysByColor.put(Color.BLUE, "ColorChooser.rgbBlueText");
      keysByColor.put(Color.YELLOW, "ColorChooser.cmykYellow.textAndMnemonic");
      keysByColor.put(Color.CYAN, "ColorChooser.cmykCyan.textAndMnemonic");
      keysByColor.put(Color.MAGENTA, "ColorChooser.cmykMagenta.textAndMnemonic");
      keysByColor.put(Color.BLACK, "ColorChooser.cmykBlack.textAndMnemonic");
    }

    String colorName = null;
    String colorKey = keysByColor.get(color);
    if (colorKey != null) {
      UIDefaults defaults = UIManagerFacade.getDefaults();
      colorName = defaults.getString(colorKey, locale);
    }

    return colorName;
  }

  private static String findColorNameEnByIntrospection(Color color) {
    if (enNamesByColor == null) {
      enNamesByColor = new HashMap<>();
      Field[] fields = Color.class.getDeclaredFields();
      for (Field field : fields) {
        if (Modifier.isStatic(field.getModifiers())) {
          try {
            String fieldName = field.getName();
            String name = canonize(fieldName);
            Object value = field.get(null);

            if ((name != null) && (value instanceof Color)) {
              enNamesByColor.put((Color) value, name);
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
