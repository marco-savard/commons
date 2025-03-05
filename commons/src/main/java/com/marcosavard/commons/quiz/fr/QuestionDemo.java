package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.geog.CountryOld;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.util.PseudoRandom;

import java.text.MessageFormat;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class QuestionDemo {

  public static void main(String[] args) {
    List<Question> questions = generateQuestions();
    Random random = new PseudoRandom(15);
    Collections.shuffle(questions, random);

    // create crossword grid
    CrosswordGrid grid = CrosswordGrid.of(5);

    grid.addWord("BLE", CrosswordGrid.Direction.HORIZONTAL, 1, 1);

    for (int cell = 0; cell <= 4; cell++) {
      boolean fit = grid.tryWord("BLANC", CrosswordGrid.Direction.VERTICAL, 0, cell);
      if (fit) {
        grid.addWord("BLANC", CrosswordGrid.Direction.VERTICAL, 0, cell);
      }
    }

    grid.print();

    for (Question question : questions) {
      //     / Console.println(question);
    }
  }

  private static void generateGrid(int size) {}

  private static List<Question> generateQuestions() {

    QuestionList questionList = new QuestionList();
    Locale display = Locale.FRENCH;
    Random random = new PseudoRandom(1);
    questionList.generateQuestions(display, random);

    List<Question> questions = new ArrayList<>();

    // generateTimeZoneCodeByName(display);s
    // script, font

    return questions;
  }

  private static void generateTimeZoneCodeByName(Locale display) {
    List<String> ids = new ArrayList<>(ZoneId.getAvailableZoneIds());
    List<Question> questions = new ArrayList<>();

    for (String id : ids) {
      ZoneId zone = ZoneId.of(id);
      String shortName = zone.getDisplayName(TextStyle.SHORT, display);
      String longName = zone.getDisplayName(TextStyle.FULL, display);

      if (shortName.length() <= 4) {
        String hint = MessageFormat.format("{0}, en anglais", longName);
        Question q = new Question(shortName, hint);
        questions.add(q);
      }
    }
  }

  private static void generateGuessDomainByCountryName(List<Question> questions, Locale display) {
    String[] countries = Locale.getISOCountries();

    for (String code : countries) {
      String countryName = CountryOld.of(code).getDisplayName(display);
      String partitive = findCountryPartitive(code, countryName);
      String hint = MessageFormat.format("Domaine internet {0}{1}", partitive, countryName);
      Question q = new Question(code, hint);
      questions.add(q);
    }
  }

  private static String findLanguageArticle(String displayLanguage) {
    String article = "le ";
    char firstLetter = displayLanguage.charAt(0);

    if ("aeiou".indexOf(firstLetter) != -1) {
      article = "l'";
    }

    return article;
  }

  private static String findCurrencyArticle(String currencyName) {
    String article = "le ";
    char firstLetter = currencyName.toLowerCase().charAt(0);

    if ("aeiou".indexOf(firstLetter) != -1) {
      article = "l'";
    } else if (currencyName.equals("rouble")) {
      article = "le ";
    } else if (currencyName.endsWith("e")) {
      article = "la ";
    }

    return article;
  }

  private static String findInabitantArticle(String displayLanguage) {
    String article = "le ";
    char firstLetter = displayLanguage.charAt(0);

    if ("aeiou".indexOf(firstLetter) != -1) {
      article = "l'";
    }

    return article;
  }

  private static String findCountryArticle(String code, String countryName) {
    String article = "le ";
    char firstLetter = StringUtil.stripAccents(countryName.toLowerCase()).charAt(0);

    if ("MX".equals(code)) {
      article = "le ";
    } else if ("aeiou".indexOf(firstLetter) != -1) {
      article = "l'";
    } else if (countryName.endsWith("e")) {
      article = "la ";
    }

    return article;
  }

  private static String findCountryPartitive(String code, String countryName) {
    String article = "du ";
    char firstLetter = StringUtil.stripAccents(countryName.toLowerCase()).charAt(0);

    if ("MX".equals(code)) {
      article = "du ";
    } else if ("aeiou".indexOf(firstLetter) != -1) {
      article = "de l'";
    } else if (countryName.endsWith("e")) {
      article = "de la ";
    }

    return article;
  }

  private static String findDeterminant(String code, String countryName) {
    String determinant = "au";
    char firstLetter = StringUtil.stripAccents(countryName.toLowerCase()).charAt(0);

    if ("BM".equals(code)) {
      determinant = "aux";
    } else if ("BS".equals(code)) {
      determinant = "aux";
    } else if ("NL".equals(code)) {
      determinant = "aux";
    } else if ("KM".equals(code)) {
      determinant = "aux";
    } else if ("MX".equals(code)) {
      determinant = "au";
    } else if ("SC".equals(code)) {
      determinant = "aux";
    } else if ("SG".equals(code)) {
      determinant = "à";
    } else if ("US".equals(code)) {
      determinant = "aux";
    } else if (countryName.endsWith("e")) {
      determinant = "en";
    } else if ("aeiou".indexOf(firstLetter) != -1) {
      determinant = "en";
    }

    return determinant;
  }

  private static List<Currency> getAvailableCurrencies() {
    List<Currency> currencies =
        Currency.getAvailableCurrencies().stream()
            .sorted(Comparator.comparing(Currency::getCurrencyCode))
            .collect(Collectors.toList());
    return currencies;
  }
}
