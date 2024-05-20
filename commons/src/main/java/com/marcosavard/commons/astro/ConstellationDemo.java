package com.marcosavard.commons.astro;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstellationDemo {

  public static void main(String[] args) {
    printNumberOfConstallations();
    printZodiacConstallations();
  }

  private static void printZodiacConstallations() {
    List<Constellation> allConstellations = Arrays.asList(Constellation.values());
    List<Constellation> zodiac =
        allConstellations.stream().filter(c -> c.isZodiac()).collect(Collectors.toList());
    System.out.println("Number of Zodiac constellations : " + zodiac.size());

    for (Constellation c : zodiac) {
      System.out.println("  " + c.getName());
    }

    System.out.println();
  }

  private static void printNumberOfConstallations() {
    List<Constellation> allConstellations = Arrays.asList(Constellation.values());
    System.out.println("Number of constellations : " + allConstellations.size());
    System.out.println();
  }
}
