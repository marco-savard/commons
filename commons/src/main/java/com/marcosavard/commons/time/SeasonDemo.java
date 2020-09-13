package com.marcosavard.commons.time;

import com.marcosavard.commons.time.Season.SeasonOf;

public class SeasonDemo {

  public static void main(String[] args) {
    Season summer1976 = Season.of(SeasonOf.SUMMER, 1976);
    System.out.println(summer1976);

    for (int year = 2020; year < 2030; year++) {
      Season summerYear = Season.of(SeasonOf.SUMMER, year);
      System.out.println(summerYear);
    }


  }

}
