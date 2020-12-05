package com.marcosavard.commons.ui;

public class ColorDemo {

  public static void main(String[] args) {
    demoBlend();
    demoContrast();
    demoColorDistance();
  }

  private static void demoBlend() {
    Color grey = Color.BLACK.blendWith(Color.WHITE);
    System.out.println("  grey : " + grey);
    System.out.println();
  }

  private static void demoContrast() {
    System.out.println("Contrast:");
    System.out.println("  black on white : " + Color.BLACK.constrastWith(Color.WHITE));
    System.out.println("  red on white : " + Color.RED.constrastWith(Color.WHITE));
    System.out.println("  yellow on white : " + Color.YELLOW.constrastWith(Color.WHITE));
    System.out.println("  green on white : " + Color.GREEN.constrastWith(Color.WHITE));
    System.out.println("  cyan on white : " + Color.CYAN.constrastWith(Color.WHITE));
    System.out.println("  blue on white : " + Color.BLUE.constrastWith(Color.WHITE));
    System.out.println();
  }

  private static void demoColorDistance() {
    Color color = Color.of(0, 0, 1);
    int distance = color.distanceFrom(Color.BLACK);
    System.out.println("  distance : " + distance);
    System.out.println();
  }

}
