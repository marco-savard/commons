package com.marcosavard.commons.ui.color;

public class GwtColorDemo {

  public static void main(String[] args) {
    demoBlend();
    demoContrast();
    demoColorDistance();
  }

  private static void demoBlend() {
    GwtColor grey = GwtColor.BLACK.blendWith(GwtColor.WHITE);
    System.out.println("  grey : " + grey);
    System.out.println();
  }

  private static void demoContrast() {
    System.out.println("Contrast:");
    System.out.println("  black on white : " + GwtColor.BLACK.constrastWith(GwtColor.WHITE));
    System.out.println("  red on white : " + GwtColor.RED.constrastWith(GwtColor.WHITE));
    System.out.println("  yellow on white : " + GwtColor.YELLOW.constrastWith(GwtColor.WHITE));
    System.out.println("  green on white : " + GwtColor.GREEN.constrastWith(GwtColor.WHITE));
    System.out.println("  cyan on white : " + GwtColor.CYAN.constrastWith(GwtColor.WHITE));
    System.out.println("  blue on white : " + GwtColor.BLUE.constrastWith(GwtColor.WHITE));
    System.out.println();
  }

  private static void demoColorDistance() {
    GwtColor color = GwtColor.of(0, 0, 1);
    int distance = color.distanceFrom(GwtColor.BLACK);
    System.out.println("  distance : " + distance);
    System.out.println();
  }

}
