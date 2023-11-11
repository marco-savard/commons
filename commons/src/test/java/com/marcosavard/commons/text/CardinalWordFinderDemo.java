package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.text.impl.EsCardinalWordFinder;

public class CardinalWordFinderDemo {
  public static void main(String[] args) {
    CardinalWordFinder finder = new EsCardinalWordFinder();
    Console.println(finder.findNorth());
    Console.println(finder.findSouth());
    Console.println(finder.findEast());
    Console.println(finder.findWest());
  }
}
