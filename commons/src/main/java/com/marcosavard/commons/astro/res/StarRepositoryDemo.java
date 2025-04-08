package com.marcosavard.commons.astro.res;

import com.marcosavard.commons.astro.star.Constellation;
import com.marcosavard.commons.res.CsvReader;
import com.marcosavard.commons.res.CsvReaderImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StarRepositoryDemo {

  public static void main(String[] args) {
    // stats
    printAll();
    printZodiac();
    findBrightestStar();
    findMostNorthernStar();
    // findStarNearestFromVernalPoint();
    printUrsaMajor();
  }

  private static void printAll() {
    List<Star> allStars = StarRepository.getStars();
    System.out.println(allStars.size() + " stars read in file StarFile.csv");
  }

  private static void printUrsaMajor() {
    List<Star> allStars = StarRepository.getStars();
    List<Star> umaStars =
        allStars.stream()
            .filter(s -> s.getConstellation().equals("UMa"))
            .collect(Collectors.toList());
    System.out.println();
    System.out.println("Stars in Ursa Major :");

    for (Star star : umaStars) {
      System.out.println("  " + star);
    }

    System.out.println();
  }

  private static void printZodiac() {
    List<Star> allStars = StarRepository.getStars();
    List<Star> zodiacStars =
        allStars.stream()
            .filter(s -> Constellation.of(s.getConstellation()).isZodiac())
            .collect(Collectors.toList());

    System.out.println(zodiacStars.size() + " stars in Zodiac constellations");
  }

  private static void findBrightestStar() {
    // sort by magnitude
    List<Star> allStars = StarRepository.getStars();
    Comparator<Star> comparator = new MagnitureComparator();
    Star brightestStar = allStars.stream().sorted(comparator).findFirst().orElse(null);
    System.out.println("Brightest star : " + brightestStar);
  }

  private static void findMostNorthernStar() {
    // sort by declination
    List<Star> allStars = StarRepository.getStars();
    Comparator<Star> comparator = new DeclinationComparator();
    Star northestStar = allStars.stream().sorted(comparator).findFirst().orElse(null);
    System.out.println("Most Northern star : " + northestStar);
  }

  /*
  private static void findStarNearestFromVernalPoint() {
  	SpaceCoordinate location = SpaceCoordinate.VERNAL_POINT;
  	Star nearestStar = StarRepository.findStarNearestFromLocation(location.getRightAscensionHour(), location.getDeclination());
  	System.out.println("Nearest star from vernal point : " + nearestStar);
  }
  */
  private static class MagnitureComparator implements Comparator<Star> {

    @Override
    public int compare(Star s1, Star s2) {
      int comparison = (int) (s1.getMagnitude() - s2.getMagnitude());
      return comparison;
    }
  }

  private static class DeclinationComparator implements Comparator<Star> {

    @Override
    public int compare(Star s1, Star s2) {
      int comparison = (int) (s2.getDeclination() - s1.getDeclination());
      return comparison;
    }
  }

  private static void printAllLines() {
    // get data
    Class<?> claz = StarFile.class;
    InputStream input = claz.getResourceAsStream("StarFile.csv");
    Charset charset = StandardCharsets.UTF_8;
    char separator = ';';
    char quoteChar = '\"';
    char headerSeparator = ';';
    String commentPrefix = "#";
    List<String[]> allLines;

    try {
      Reader reader = new InputStreamReader(input, charset.name());
      CsvReader csvReader =
          new CsvReaderImpl(reader, separator, quoteChar, headerSeparator, commentPrefix);
      allLines = csvReader.readAll();
      csvReader.close();
    } catch (IOException e) {
      allLines = new ArrayList<>();
      e.printStackTrace();
    }

    // print all
    for (String[] line : allLines) {
      String str = "  [" + String.join("; ", line) + "]";
      System.out.println(str);
    }

    System.out.println();
  }
}
