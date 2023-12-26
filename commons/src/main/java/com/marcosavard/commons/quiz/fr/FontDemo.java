package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.debug.Console;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FontDemo {

  public static void main(String[] args) {

    List<String> fonts = getFontNames();

    for (String font : fonts) {
      Console.println(font);
    }
  }

  private static List<String> getFontNames() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Font[] allFonts = ge.getAllFonts();
    List<String> fonts = new ArrayList<>();

    for (Font font : allFonts) {
      String fontName = font.getFontName(Locale.FRENCH).toLowerCase();

      if (fontName.contains("gras")) {
        fontName = fontName.replace("microsoft", "");
        fontName = fontName.replace("linotype", "");
        fontName = fontName.replace("italique", "");
        fontName = fontName.replace("roman", "");
        fontName = fontName.replace("gras", "");
        fontName = fontName.replace("sans", "");
        fontName = fontName.replace("new", "");
        fontName = fontName.replace("ui", "");
        fontName = fontName.replace("ms", "").trim();

        if (!fonts.contains(fontName)) {
          fonts.add(fontName);
        }
      }
    }

    return fonts;
  }
}
