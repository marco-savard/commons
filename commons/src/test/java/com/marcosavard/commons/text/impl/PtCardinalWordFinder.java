package com.marcosavard.commons.text.impl;

import com.marcosavard.commons.text.CardinalWordFinder;
import com.marcosavard.commons.text.WordFinder;

import java.util.List;

public class PtCardinalWordFinder extends CardinalWordFinder {

  public PtCardinalWordFinder() {
    super("pt", List.of(MX, KP, KR), List.of(NY, CHI, LA, ANCHOR, SANTA_ISABEL));
  }

  @Override
  public String findNorth() {
    String s1 = countryNames.get(KP);
    String s2 = countryNames.get(KR);
    String north = WordFinder.difference(s2, s1).toLowerCase();
    return north;
  }

  @Override
  public String findSouth() {
    String s1 = countryNames.get(KP);
    String s2 = countryNames.get(KR);
    String south = WordFinder.difference(s1, s2).toLowerCase();
    return south;
  }

  @Override
  public String findEast() {
    String s1 = timezoneNames.get(NY);
    String s2 = timezoneNames.get(CHI);
    String s3 = timezoneNames.get(ANCHOR);
    String east = WordFinder.difference(s3, s1).toLowerCase();
    return east;
  }

  @Override
  public String findWest() {
    String s1 = timezoneNames.get(SANTA_ISABEL).toLowerCase();
    String s2 = timezoneNames.get(LA).toLowerCase();
    String s3 = countryNames.get(MX).toLowerCase();
    String north = findNorth();
    String delta = WordFinder.removeFrom(s1, s2);
    String nw = WordFinder.removeFrom(delta, s3);
    String common = WordFinder.findLongestSubstring(nw, north);
    String west = nw.replace(common, "");
    return west;
  }
}
