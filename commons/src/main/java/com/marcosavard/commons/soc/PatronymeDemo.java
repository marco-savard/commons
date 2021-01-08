package com.marcosavard.commons.soc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This demo class illustrates common uses of the Patronyme class.
 * 
 * @author Marco
 *
 */
public class PatronymeDemo {

  public static void main(String[] args) {
    createPatronymes();
    getCommonPatronymes();
    findClosestPatronymes();
    sanitizePatronyme();
  }

  private static void createPatronymes() {
    // padding blanks are ignored
    Patronyme p1 = new Patronyme("roy");
    Patronyme p2 = new Patronyme("Roy  ");
    String msg = MessageFormat.format("  {0} is same that {1} : {2}", p1, p2, p1.equals(p2));
    System.out.println(msg);
    System.out.println();

    // invalid characters are removed
    System.out.println("Patronymes with invalid characters removed");
    System.out.println(new Patronyme("O'Neil)"));
    System.out.println(new Patronyme("LeBlanc"));
    System.out.println(new Patronyme("St-Cyr"));
    System.out.println(new Patronyme("Pierre //%&"));
    System.out.println();
  }

  private static void getCommonPatronymes() {
    // list 20 most popular patronymes
    System.out.println("Ten most common patronymes:");
    List<Patronyme> patronymes = PatronymeRepository.getPatronymes();
    List<Patronyme> mostPopulars = patronymes.subList(0, 10);

    for (Patronyme p : mostPopulars) {
      int idx = patronymes.indexOf(p);
      String msg = MessageFormat.format("  {0}) {1}", idx + 1, p);
      System.out.println(msg);
    }

    System.out.println();

    // patronymes starting with 'W'
    System.out.println("Patronymes starting with W");
    Predicate<Patronyme> pred = p -> p.getName().startsWith("W");
    List<Patronyme> patronymesInT = patronymes.stream().filter(pred).collect(Collectors.toList());
    for (Patronyme p : patronymesInT) {
      int idx = patronymes.indexOf(p);
      String msg = MessageFormat.format("  {0}) {1}", idx + 1, p);
      System.out.println(msg);
    }

    System.out.println();

    // patronymes that are hagionymes (names of Saints)
    System.out.println("Patronymes starting with St-");
    String regex = "St-.*";
    List<Patronyme> hagionymes =
        patronymes.stream().filter(p -> p.getName().matches(regex)).collect(Collectors.toList());
    for (Patronyme p : hagionymes) {
      int idx = patronymes.indexOf(p);
      String msg = MessageFormat.format("  {0}) {1}", idx + 1, p);
      System.out.println(msg);
    }

    System.out.println();
  }

  private static void findClosestPatronymes() {
    String[] mispelledNames = new String[] {"Tramblay", "Marin", "Gautier", "Peletier", "Lefevre",
        "Perreau", "Boudreault", "Terrien", "Arsenau"};

    for (String name : mispelledNames) {
      Patronyme p = PatronymeRepository.findMostSimilarPatronyme(name);
      String msg = MessageFormat.format("  {0} -> {1}", name, p);
      System.out.println(msg);
    }


    System.out.println();
  }

  private static void sanitizePatronyme() {
    System.out.println("Enter a family name and press ENTER (empty line to exit): ");
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    try {
      do {
        String line = bf.readLine();

        if (line.isEmpty()) {
          break;
        } else {
          Patronyme patronyme = new Patronyme(line);
          System.out.println("  Family name sanitized : \"" + patronyme + "\"");
        }
      } while (true);
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Terminated");
  }



}
