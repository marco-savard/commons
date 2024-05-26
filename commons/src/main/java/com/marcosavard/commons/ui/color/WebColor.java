package com.marcosavard.commons.ui.color;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WebColor extends Color {
  private static final double RED_LUMINENCE = 0.299;
  private static final double GREEN_LUMINENCE = 0.587;
  private static final double BLUE_LUMINENCE = 0.114;
  private static Map<String, Color> colorsByName = null;
  private static Map<Integer, String> namesByRGB = null;
  private static List<Color> namedColors = null;

  private final float[] hsb;

  public static WebColor of(Color color) {
    return new WebColor(color.getRGB());
  }

  public static WebColor ofRGB(int rgb) {
    return new WebColor(rgb);
  }

  public static WebColor ofRGB(int r, int g, int b) {
    return new WebColor(r, g, b);
  }

  private WebColor(int rgb) {
    super(rgb);
    hsb = Color.RGBtoHSB(getRed(), getGreen(), getBlue(), null);
  }

  private WebColor(int r, int g, int b) {
    super(r, g, b);
    hsb = Color.RGBtoHSB(r, g, b, null);
  }

  public float[] getHsb() {
    return hsb;
  }

  public float getHue() {
    return hsb[0];
  }

  public float getSaturation() {
    return hsb[1];
  }

  public float getBrightness() {
    return hsb[2];
  }

  @Override
  public String toString() {
    return toString(this);
  }

  public String getDisplayName() {
    return toString(this);
  }

  public static String toString(Color color) {
    String name = getNamesByRGB().get(color.getRGB());
    String hex = "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
    name = (name != null) ? name : hex;
    return name;
  }

  @Override
  public boolean equals(Object o) {
    Color that = (o instanceof Color) ? (Color) o : null;
    boolean equal = (that != null) && (getRGB() == that.getRGB());
    return equal;
  }

  @Override
  public int hashCode() {
    int code = (getRed() * 13) + (getGreen() * 7) + getBlue();
    return code;
  }

  /**
   * Return the grayscale of a color.
   *
   * @return a color (white, grey or black)
   */
  public WebColor toGrayscale() {
    int luminence =
        (int)
            ((RED_LUMINENCE * getRed())
                + (GREEN_LUMINENCE * getGreen())
                + (BLUE_LUMINENCE * getBlue()));
    return WebColor.ofRGB(luminence, luminence, luminence);
  }

  public WebColor blendWith(Color thatColor) {
    WebColor blended;

    if (this.equals(Color.YELLOW) && thatColor.equals(Color.BLUE)) {
      blended = WebSafeColor.GREEN.getColor();
    } else if (this.equals(Color.BLUE) && thatColor.equals(Color.YELLOW)) {
      blended = WebSafeColor.GREEN.getColor();
    } else {
      int r = (this.getRed() + thatColor.getRed()) / 2;
      int g = (this.getGreen() + thatColor.getGreen()) / 2;
      int b = (this.getBlue() + thatColor.getBlue()) / 2;
      blended = WebColor.ofRGB(r, g, b);
    }

    return blended;
  }

  public int distanceFrom(Color thatColor) {
    int distance = Math.abs(this.getRed() - thatColor.getRed());
    distance += Math.abs(this.getGreen() - thatColor.getGreen());
    distance += Math.abs(this.getBlue() - thatColor.getBlue());
    return distance;
  }

  public double constrastWith(Color thatColor) {
    double luminence =
        (RED_LUMINENCE * getRed()) + (GREEN_LUMINENCE * getGreen()) + (BLUE_LUMINENCE * getBlue());
    double thatLuminence =
        (RED_LUMINENCE * thatColor.getRed())
            + (GREEN_LUMINENCE * thatColor.getGreen())
            + (BLUE_LUMINENCE * thatColor.getBlue());
    double contrast = Math.abs(luminence - thatLuminence) / 255.0;
    return contrast;
  }

  // static
  public static Map<String, Color> getColorsByName() {
    init();
    return colorsByName;
  }

  public static Map<Integer, String> getNamesByRGB() {
    init();
    return namesByRGB;
  }

  public static List<Color> getNamedColors() {
    init();
    return namedColors;
  }

  private static void init() {
    if (colorsByName == null) {
      colorsByName = new TreeMap<>();
      namesByRGB = new TreeMap<>();
      namedColors = new ArrayList<>();
      Class claz = Color.class;
      Field[] fields = claz.getDeclaredFields();

      for (Field field : fields) {
        int mod = field.getModifiers();
        boolean constant = Modifier.isStatic(mod) && Modifier.isFinal(mod);

        if (constant) {
          try {
            String name = field.getName().toLowerCase();
            Color color = (Color) field.get(null);
            colorsByName.put(name, color);
            namesByRGB.put(color.getRGB(), name);
            namedColors.add(color);
          } catch (IllegalAccessException e) {
            // ignore and continue
          }
        }
      }
    }
  }
}
