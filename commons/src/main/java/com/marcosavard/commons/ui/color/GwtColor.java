package com.marcosavard.commons.ui.color;

import java.io.Serializable;

/**
 * Define a color in term of RGB (Red, Green and Blue) components. Provide method to blend colors,
 * to calculate contrast of colors, to compute hue of a color, saturation and luminence of colors.
 * This class is purely mathematical, thus it can be used in an environment like GWT (Google Widget
 * Toolkit), where imports of AWT or Swing library is prohibited.
 * 
 * 
 * @author Marco
 *
 */
// A type-safe color type
// Replaces java.awt.Color, which is not supported in GWT
@SuppressWarnings("serial")
public class GwtColor implements Serializable {
  private static final double RED_LUMINENCE = 0.299;
  private static final double GREEN_LUMINENCE = 0.587;
  private static final double BLUE_LUMINENCE = 0.114;

  // constants
  public static final GwtColor BLACK = new GwtColor(0, 0, 0);
  public static final GwtColor RED = new GwtColor(255, 0, 0);
  public static final GwtColor YELLOW = new GwtColor(255, 255, 0);
  public static final GwtColor GREEN = new GwtColor(0, 127, 0);
  public static final GwtColor LIME = new GwtColor(0, 255, 0);
  public static final GwtColor CYAN = new GwtColor(0, 255, 255);
  public static final GwtColor BLUE = new GwtColor(0, 0, 255);
  public static final GwtColor MAGENTA = new GwtColor(0, 255, 255);
  public static final GwtColor WHITE = new GwtColor(255, 255, 255);

  public static final GwtColor DARK_GRAY = new GwtColor(63, 63, 63);
  public static final GwtColor GRAY = new GwtColor(127, 127, 127);
  public static final GwtColor LIGHT_GRAY = new GwtColor(215, 215, 215);
  public static final GwtColor ORANGE = new GwtColor(255, 127, 0);
  public static final GwtColor PINK = new GwtColor(255, 127, 127);

  private final int red, green, blue;
  private double alpha;

  protected GwtColor(int rgb) {
    this.red = (rgb & 0xff0000) / (256 * 256);
    this.green = (rgb & 0x00ff00) / 256;
    this.blue = rgb & 0x0000ff;
  }

  /**
   * Create an immutable color based on Red, Green, Blue and Alpha channel.
   * 
   * @param red in the range 0..255
   * @param green in the range 0..255
   * @param blue in the range 0..255
   * @param alpha in the range 0.0 1.0 (default value 1.0)
   */
  public static GwtColor of(int red, int green, int blue, double alpha) {
    GwtColor color;

    if ((red == 0) && (green == 0) && (blue == 0)) {
      color = BLACK;
    } else if ((red == 0) && (green == 0) && (blue == 0)) {
      color = WHITE;
    } else {
      color = new GwtColor(red, green, blue, alpha);
    }

    return color;
  }

  public static GwtColor of(int red, int green, int blue) {
    return GwtColor.of(red, green, blue, 1.0);
  }

  public static GwtColor of(int rgb) {
    int red = (rgb & 0xff0000) / (256 * 256);
    int green = (rgb & 0x00ff00) / 256;
    int blue = rgb & 0x0000ff;
    return GwtColor.of(red, green, blue, 1.0);
  }

  // required by GWT
  private GwtColor() {
    this(0, 0, 0, 0);
  }

  protected GwtColor(int red, int green, int blue) {
    this(red, green, blue, 1.0);
  }

  private GwtColor(int red, int green, int blue, double alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }



  /*
   * 
   * public Color(String rgb) { red = Integer.parseInt(rgb.substring(0, 2), 16); green =
   * Integer.parseInt(rgb.substring(2, 4), 16); blue = Integer.parseInt(rgb.substring(4, 6), 16); }
   */

  public int getRed() {
    return red;
  }

  public int getGreen() {
    return green;
  }

  public int getBlue() {
    return blue;
  }

  public int getValue() {
    int value = red << 16 | green << 8 | blue;
    return value;
  }

  @Override
  public String toString() {
    String r = Integer.toHexString(0x100 | red).substring(1);
    String g = Integer.toHexString(0x100 | green).substring(1);
    String b = Integer.toHexString(0x100 | blue).substring(1);
    String str = "#" + r + g + b;
    return str;
  }


  public String getHexCode() {
    StringBuffer buf = new StringBuffer("#");
    buf.append((red <= 9) ? "0" : "");
    buf.append(Integer.toHexString(red));

    buf.append((green <= 9) ? "0" : "");
    buf.append(Integer.toHexString(green));

    buf.append((blue <= 9) ? "0" : "");
    buf.append(Integer.toHexString(blue));

    String hex = buf.toString().toUpperCase();
    return hex;
  }

  /**
   * Represents a color as a RGB string, that can be used in HTML.
   * 
   * @return textual representation of a color
   */
  public String toRGBString() {
    String rgbString = "rgb(" + red + "," + green + "," + blue + ")";
    return rgbString;
  }

  /**
   * Represents a color as a RGBA string, that can be used in HTML.
   * 
   * @return textual representation of a color
   */
  public String toRGBAString() {
    String rgbString = "rgba(" + red + "," + green + "," + blue + "," + alpha + ")";
    return rgbString;
  }

  /**
   * Converts a string formatted as #ff00ff to a Color instance.
   * 
   * @param value such as #ff00ff
   * @return a color instance
   */
  public static GwtColor fromString(String value) {
    GwtColor color;

    try {
      String r = value.substring(1, 3);
      String g = value.substring(3, 5);
      String b = value.substring(5, 7);

      int red = Integer.parseInt(r, 16);
      int green = Integer.parseInt(g, 16);
      int blue = Integer.parseInt(b, 16);
      color = new GwtColor(red, green, blue);
    } catch (RuntimeException ex) {
      color = null;
    }

    return color;
  }

  @Override
  public boolean equals(Object o) {
    GwtColor that = (o instanceof GwtColor) ? (GwtColor) o : null;
    boolean equal = that != null;

    equal &= (that.red == red);
    equal &= (that.green == green);
    equal &= (that.blue == blue);

    return equal;
  }

  @Override
  public int hashCode() {
    int code = (red * 13) + (green * 7) + blue;
    return code;
  }

  /**
   * Return the grayscale of a color.
   * 
   * @return a color (white, grey or black)
   */
  public GwtColor toGrayscale() {
    int luminence =
        (int) ((RED_LUMINENCE * red) + (GREEN_LUMINENCE * green) + (BLUE_LUMINENCE * blue));
    return GwtColor.of(luminence, luminence, luminence);
  }

  /**
   * Build a color brighter from a given color
   * 
   * @param factor from 1.0 to 100.0 (1.2 default)
   * @return a brighter color
   */
  public GwtColor brighter(double factor) {
    float[] hsb = toHSB();
    int hue = (int) (radToDegree(hsb[0]));
    int saturation = (int) (100 * hsb[1]);
    int brightness = (int) (100 * hsb[2] * factor);
    brightness = (brightness > 100) ? 100 : brightness;

    GwtColor brighterColor = GwtColor.createFromHsl(hue, saturation, brightness);
    return brighterColor;
  }

  public GwtColor brighter() {
    return brighter(1.2);
  }

  /**
   * Build a color darker from a given color
   * 
   * @param factor from 1.0 to 100.0 (1.2 default)
   * @return a darker color
   */
  public GwtColor darker(double factor) {
    GwtColor darkerColor = brighter(1.0 / factor);
    return darkerColor;
  }

  public GwtColor darker() {
    return darker(1.2);
  }

  /**
   * Return the contrast (from 0.0 to 1.0) with that color
   * 
   * @param thatColor another color
   * @return contrast from 0.0 (no contrast) and 1.0 (full contrast)
   */
  public double constrastWith(GwtColor thatColor) {
    double thisLuminence =
        (RED_LUMINENCE * red) + (GREEN_LUMINENCE * green) + (BLUE_LUMINENCE * blue);
    double otherLuminence = (RED_LUMINENCE * thatColor.red) + (GREEN_LUMINENCE * thatColor.green)
        + (BLUE_LUMINENCE * thatColor.blue);
    double contrast = Math.abs(thisLuminence - otherLuminence) / 255.0;
    return contrast;
  }

  /**
   * Return black or white, depending which color gives the highest contrast. For instance,
   * yellow.getContrastColor() gives black, while darkBlue.getContrastColor() gives white.
   * 
   * @return Black or white
   * 
   */
  public GwtColor toContrastColor() {
    int brightness = getBrightness();
    GwtColor contrast = (brightness > 50) ? GwtColor.BLACK : GwtColor.WHITE;
    return contrast;
  }

  /**
   * Return the complimentary color. For instance getComplimentaryColor(black) returns white.
   * 
   * @return complimentary color.
   */
  public GwtColor toComplimentaryColor() {
    int r = 255 - red;
    int g = 255 - green;
    int b = 255 - blue;
    return GwtColor.of(r, g, b);
  }

  /**
   * Blend this color with that color
   * 
   * @param thatColor the color to blend with
   * @param thatPercent the percentage of the color to blend (default value : 50)
   * @return blended color
   */
  public GwtColor blendWith(GwtColor thatColor, int thatPercent) {
    int thisPercent = 100 - thatPercent;
    int r = (red * thisPercent + thatColor.red * thatPercent) / 100;
    int g = (green * thisPercent + thatColor.green * thatPercent) / 100;
    int b = (blue * thisPercent + thatColor.blue * thatPercent) / 100;

    GwtColor color = new GwtColor(r, g, b);
    return color;
  }

  public GwtColor blendWith(GwtColor otherColor) {
    int r = (red + otherColor.red) / 2;
    int g = (green + otherColor.green) / 2;
    int b = (blue + otherColor.blue) / 2;

    GwtColor color = new GwtColor(r, g, b);
    return color;
  }

  /**
   * Convert RGB components to HSB (hue, saturation, brightness)
   * 
   * @return array of 3 numbers, representing the hue, saturation and brightness
   */
  public float[] toHSB() {
    double y = Math.sqrt(3.0) * (green - blue);
    double x = (2.0 * red) - green - blue;
    double h = Math.atan2(y, x);

    int min = Math.min(Math.min(red, green), blue);
    int max = Math.max(Math.max(red, green), blue);
    int delta = max - min;
    double s = (max == 0) ? 0 : delta / (double) max;

    double b = (((max + min) / 2) / 255.0);

    float[] hsb = new float[] {(float) h, (float) s, (float) b};
    return hsb;
  }

  /**
   * Get the hue of a color. Hue varies from red (0.0), green (120.0), ble (240.0) and red again
   * (360.0).
   * 
   * @return a number from 0.0 (red) to 360.0 (red)
   */
  public int getHue() {
    float[] hsb = toHSB();
    double degrees = (hsb[0] * (180 / Math.PI));
    degrees = (degrees + 360.0) % 360;
    return (int) degrees;
  }

  /**
   * Get saturation
   * 
   * @return saturation
   */
  public int getSaturation() {
    float[] hsb = toHSB();
    return (int) (hsb[1] * 100);
  }

  /**
   * Get brightness
   * 
   * @return brightness
   */
  public int getBrightness() {
    float[] hsb = toHSB();
    return (int) (hsb[2] * 100);
  }

  /**
   * Convert RGB to CMYK values. C is for cyan, M for magenta, Y for yellow.
   * 
   * @return array of four numbers representing CMYK values
   */
  public float[] getCMYK() {
    double rp = red / 255.0;
    double gp = green / 255.0;
    double bp = blue / 255.0;

    double k = 1.0 - Math.max(rp, Math.max(gp, bp));
    double kr = 1.0 - k;
    double c = (1.0 - rp - k) / kr;
    double m = (1.0 - gp - k) / kr;
    double y = (1.0 - bp - k) / kr;

    float[] cmyk = new float[] {(float) c, (float) m, (float) y, (float) k};
    return cmyk;
  }

  /**
   * Create a color, from HSB instead of RGB values
   * 
   * @param hue in the range [0..360]
   * @param saturation in percentage
   * @param lightness in percentage
   * @return a color
   */
  public static GwtColor createFromHsl(int hue, int saturation, int lightness) {
    hue = (hue >= 360) ? hue % 360 : hue;
    int h = (int) ((hue / 360.0) * 6);
    double f = (hue / 360.0) * 6 - h;
    double v = lightness / 100.0;
    double p = v * (1 - (saturation / 100.0));
    double q = v * (1 - f * (saturation / 100.0));
    double t = v * (1 - (1 - f) * (saturation / 100.0));
    double r, g, b;

    switch (h) {
      case 0: {
        r = v;
        g = t;
        b = p;
      }
        break;
      case 1: {
        r = q;
        g = v;
        b = p;
      }
        break;
      case 2: {
        r = p;
        g = v;
        b = t;
      }
        break;
      case 3: {
        r = p;
        g = q;
        b = v;
      }
        break;
      case 4: {
        r = t;
        g = p;
        b = v;
      }
        break;
      case 5: {
        r = v;
        g = p;
        b = p;
      }
        break;

      default:
        throw new RuntimeException(
            "Something went wrong when converting from HSV to RGB. Input was " + hue + ", "
                + saturation + ", " + v);
    }

    GwtColor color = new GwtColor((int) (r * 255), (int) (g * 255), (int) (b * 255));
    return color;
  }


  public Hue getHueCode() {
    int value = getHue();
    Hue hue = Hue.RED;

    if (value < 15) {
      hue = Hue.RED;
    } else if (value < 45) {
      hue = Hue.ORANGE;
    } else if (value < 75) {
      hue = Hue.YELLOW;
    } else if (value < 180) {
      hue = Hue.GREEN;
    } else if (value < 200) {
      hue = Hue.CYAN;
    } else if (value < 270) {
      hue = Hue.BLUE;
    } else if (value < 345) {
      hue = Hue.PURPLE;
    }
    return hue;
  }

  public int getRGB() {
    return (red * 256 * 256) + (green * 256) + blue;
  }

  public enum Hue {
    RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE
  };

  private double radToDegree(double radian) {
    double degrees = (radian * 180) / Math.PI;
    degrees = (degrees > 0) ? (degrees % 360) : (degrees + 360);
    return degrees;
  }

  public int distanceFrom(GwtColor other) {
    int distance = Math.abs(this.red - other.red);
    distance += Math.abs(this.green - other.green);
    distance += Math.abs(this.blue - other.blue);
    return distance;
  }



}
