package com.marcosavard.common.ui;

import com.marcosavard.common.ui.res.ColorNameResource;
import com.marcosavard.common.ui.res.UIManagerFacade;

import javax.swing.*;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.*;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

public class ColorInfo {
    private static List<ColorInfo> colorInfos = new ArrayList<>();
    private static Map<Color, String> colorUiKeys = new HashMap<>();
    private static ColorNameResource colorNameResource = new ColorNameResource();

    private final Color color;
    private final String fieldName;

    private ColorInfo(Color color, String fieldName) {
        this.color = color;
        this.fieldName = fieldName;
    }

    public static ColorInfo of(Color color) {
        List<ColorInfo> colorInfos = getColorInfos();
        ColorInfo colorInfo = colorInfos.stream()
                .filter(i -> i.color.equals(color))
                .findFirst()
                .orElse(null);

        if (colorInfo == null) {
            colorInfo = new ColorInfo(color, null);
            colorInfos.add(colorInfo);
        }

        return colorInfo;
    }

    public static List<ColorInfo> getColorInfos() {
        if (colorInfos.isEmpty()) {
            initColorInfos(); 
        }

        return colorInfos;
    }

    public static Color[] getConstantColors() {
        List<ColorInfo> colorInfos = ColorInfo.getColorInfos();
        List<Color> colors = colorInfos.stream().map(i -> i.color).toList();
        return colors.toArray(new Color[0]);
    }

    @Override
    public String toString() {
        return fieldName;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ColorInfo otherColor) {
            return color.getRGB() == otherColor.color.getRGB();
        } else {
            return false;
        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getDisplayName(Locale display) {
        String displayName = findInUIDefaults(display);

        if ((displayName == null) && (fieldName != null)) {
            String key = fieldName.toLowerCase();
            displayName = colorNameResource.getColorName(key, display);
        }

        if (displayName == null) {
            displayName = String.format("#%06X", (0xFFFFFF & color.getRGB()));
        }

        return displayName;
    }

    private String findInUIDefaults(Locale display) {
        UIDefaults defaults = UIManagerFacade.getDefaults();
        Map<Color, String> colorKeys = getColorUiKeys();
        String key = colorKeys.get(color);
        return (key == null) ? null : defaults.get(key, display).toString().toLowerCase();
    }

    //
    // private methods
    //

    private static void initColorInfos() {
        List<Field> fields = getColorFields();

        for (Field field : fields) {
            try {
                Object value = field.get(null);

                if (value instanceof Color color) {
                    String fieldName = field.getName();
                    
                    if (isUpperCase(fieldName)) {
                        ColorInfo colorInfo = new ColorInfo(color, fieldName);

                        if (! colorInfos.contains(colorInfo)) {
                            colorInfos.add(colorInfo);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean isUpperCase(String fieldName) {
        return fieldName.equals(fieldName.toUpperCase());
    }

    private static List<Field> getColorFields() {
        Field[] declaredFields = Color.class.getDeclaredFields();
        List<Field> colorFields = new ArrayList<>();

        for (Field field : declaredFields) {
            int mods = field.getModifiers();
            if (isPublic(mods) && isStatic(mods)) {
                try {
                    Object value = field.get(null);

                    if (value instanceof Color) {
                        colorFields.add(field);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return colorFields;
    }

    private static Map<Color, String> getColorUiKeys() {
        if (colorUiKeys.isEmpty()) {
            colorUiKeys.put(Color.RED, "ColorChooser.rgbRedText");
            colorUiKeys.put(Color.GREEN, "ColorChooser.rgbGreenText");
            colorUiKeys.put(Color.BLUE, "ColorChooser.rgbBlueText");
            colorUiKeys.put(Color.CYAN, "ColorChooser.cmykCyan.textAndMnemonic");
            colorUiKeys.put(Color.MAGENTA, "ColorChooser.cmykMagenta.textAndMnemonic");
            colorUiKeys.put(Color.YELLOW, "ColorChooser.cmykYellow.textAndMnemonic");
            colorUiKeys.put(Color.BLACK, "ColorChooser.cmykBlack.textAndMnemonic");
        }

        return colorUiKeys;
    }

    private static String normalize(String fieldName) {
        boolean uppercase = fieldName.endsWith(fieldName.toUpperCase());
        String canonized = null;

        if (uppercase) {
            fieldName = fieldName.replace('_', ' ');
            canonized = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1).toLowerCase();
        }

        return canonized;
    }
}
