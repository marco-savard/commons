package com.marcosavard.commons.types;

public class ColorDemo {

  public static void main(String[] args) {
    Color primary = Color.of(255, 0, 0);

    int color = 0x00ff00;
    int green = color & 0x00ff00;
    green = green / 256;

    System.out.println(" green : " + green);

    // blend with

    // constrast

  }

}
