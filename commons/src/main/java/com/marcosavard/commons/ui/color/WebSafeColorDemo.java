package com.marcosavard.commons.ui.color;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.math.arithmetic.PseudoRandom;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

public class WebSafeColorDemo {
  public static void main(String[] args) {
    Locale display = Locale.FRENCH;
    printColors(display);
    // blendColor(display);
    // chooseBasicColors(50, display);
    // chooseColorHard(50, display);
    // chooseColorByTint(display);
    // chooseHuelessColor(display);
  }

  private static void printColors(Locale display) {
    List<WebSafeColor> colors = List.of(WebSafeColor.values());

    for (WebSafeColor color : colors) {
      Console.println(color.getDisplayName(display));
    }
  }

  private static void chooseHuelessColor(Locale display) {
    WebSafeColor[] allColors = WebSafeColor.values();

    for (WebSafeColor color : allColors) {
      float s = color.getColor().getSaturation();

      if (s < 0.1) {
        String hint = "couleur sens teinte ";
        String answer = color.getDisplayName(display);
        Console.println(hint + " : " + answer);
      } else if (s >= 0.1 && s < 0.4) {
        String hint = "couleur de faible teinte ";
        String answer = color.getDisplayName(display);
        Console.println(hint + " : " + answer);
      }
    }
  }

  private static void chooseColorByTint(Locale display) {
    WebSafeColor[] allColors = WebSafeColor.values();
    List<GwtColor> tints =
        List.of(GwtColor.RED, GwtColor.ORANGE, GwtColor.YELLOW, GwtColor.GREEN, GwtColor.BLUE, GwtColor.MAGENTA);

    for (GwtColor tint : tints) {
      chooseColorByTint(allColors, tint, display);
    }
  }

  private static void chooseColorByTint(WebSafeColor[] allColors, GwtColor tint, Locale display) {
    float hue = WebColor.of(tint).getHue();
    String n = WebSafeColor.findClosestColor(tint).getDisplayName(display);
    n = StringUtil.startWithVowel(n) ? "de l'" + n : "du " + n;

    for (WebSafeColor color : allColors) {
      float s = color.getColor().getSaturation();
      float h = color.getColor().getHue();

      if ((s > 0.5) && (Math.abs(hue - h) < 0.2)) {
        String hint = "couleur proche " + n;
        String answer = color.getDisplayName(display);
        Console.println(hint + " : " + answer);
      }
    }
  }

  private static void chooseBasicColors(int count, Locale display) {
    List<WebSafeColor> colors = List.of(WebSafeColor.values());
    PseudoRandom r = new PseudoRandom(3);

    for (int i = 0; i < count; i++) {
      GwtColor c1, c2;
      WebSafeColor w1, w2;
      WebSafeColor blendColor;
      boolean valid = false;
      List<GwtColor> namedColors = WebColor.getNamedColors();

      do {
        c1 = namedColors.get(r.nextInt(namedColors.size()));
        c2 = namedColors.get(r.nextInt(namedColors.size()));
        String n1 = WebColor.toString(c1).toUpperCase();
        String n2 = WebColor.toString(c2).toUpperCase();
        w1 = WebSafeColor.valueOf(n1);
        w2 = WebSafeColor.valueOf(n2);
        blendColor = blend(w1, w2, display);
        valid = !c1.equals(blendColor) && !c2.equals(blendColor);
      } while (!valid);

      printResult(w1, w2, blendColor, display);
    }
  }

  private static void chooseColorHard(int count, Locale display) {
    List<WebSafeColor> colors = List.of(WebSafeColor.values());
    PseudoRandom r = new PseudoRandom(3);

    for (int i = 0; i < count; i++) {
      WebSafeColor c1, c2, blendColor;
      boolean valid;

      do {
        c1 = colors.get(r.nextInt(colors.size()));
        c2 = colors.get(r.nextInt(colors.size()));
        blendColor = blend(c1, c2, display);
        valid = !c1.equals(blendColor) && !c2.equals(blendColor);
      } while (!valid);

      printResult(c1, c2, blendColor, display);
    }
  }

  private static void blendColor(Locale display) {
    List<WebSafeColor> colors = List.of(WebSafeColor.values());

    //
    blend(WebSafeColor.YELLOW, WebSafeColor.GREEN, display);
    blend(WebSafeColor.GREEN, WebSafeColor.BLUE, display);
    blend(WebSafeColor.BLUE, WebSafeColor.RED, display);

    // blend(WebSafeColor.BLACK, WebSafeColor.WHITE, display);
    blend(WebSafeColor.RED, WebSafeColor.WHITE, display);
    blend(WebSafeColor.CORAL, WebSafeColor.WHITE, display);
    blend(WebSafeColor.RED, WebSafeColor.YELLOW, display);
    blend(WebSafeColor.YELLOW, WebSafeColor.WHITE, display);
    blend(WebSafeColor.GREEN, WebSafeColor.WHITE, display);
    blend(WebSafeColor.BLUE, WebSafeColor.WHITE, display);
  }

  private static WebSafeColor blend(WebSafeColor c1, WebSafeColor c2, Locale display) {
    GwtColor blend = c1.getColor().blendWith(c2.getColor());
    WebSafeColor blendColor;

    do {
      blendColor = WebSafeColor.findClosestColor(blend);
      float[] hsb = blendColor.getColor().getHsb();
    } while (false);

    return blendColor;
  }

  private static void printResult(
      WebSafeColor c1, WebSafeColor c2, WebSafeColor blendColor, Locale display) {
    String s1 = c1.getDisplayName(display);
    String s2 = c2.getDisplayName(display);
    String s3 = blendColor.getDisplayName(display);
    int rgb = blendColor.getColor().getRGB();
    String blendCode = "0x" + Integer.toHexString(rgb).substring(2).toUpperCase();
    String msg = MessageFormat.format("{0} et {1} donnent {2} ({3})", s1, s2, s3, blendCode);
    Console.println(msg);
  }
}
