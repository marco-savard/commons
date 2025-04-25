package com.marcosavard.commons.ui.color;

import com.marcosavard.commons.debug.Console;

import java.util.Map;

public class WebColorDemo {

  public static void main(String[] args) {
    demoBlend(GwtColor.BLACK, GwtColor.WHITE);
    demoColorDistance(GwtColor.BLACK, GwtColor.BLUE);
    demoContrast(GwtColor.BLACK, GwtColor.WHITE);
    demoContrast(GwtColor.BLUE, GwtColor.WHITE);
    demoContrast(GwtColor.RED, GwtColor.WHITE);
    // demoContrast(Color.YELLOW, Color.WHITE);
    demoConstants();
  }

  private static void demoConstants() {
    Map<String, GwtColor> constants = WebColor.getColorsByName();
  }

  private static void demoContrast(GwtColor c1, GwtColor c2) {
    double contrast = WebColor.of(c1).constrastWith(c2);
    String pat = "Constrast between {0} and {1} : {2} ";
    Console.println(pat, WebColor.toString(c1), WebColor.toString(c2), contrast);
  }

  private static void demoBlend(GwtColor c1, GwtColor c2) {
    WebColor blend = WebColor.of(c1).blendWith(c2);
    Console.println("{0} and {1} gives {2}", WebColor.toString(c1), WebColor.toString(c2), blend);
  }

  private static void demoColorDistance(GwtColor c1, GwtColor c2) {
    int distance = WebColor.of(c1).distanceFrom(c2);
    String pat = "Distance between {0} and {1} : {2} ";
    Console.println(pat, WebColor.toString(c1), WebColor.toString(c2), distance);
  }
}
