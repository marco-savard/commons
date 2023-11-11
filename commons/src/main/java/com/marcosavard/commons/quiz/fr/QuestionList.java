package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.chem.ChemicalElement;
import com.marcosavard.commons.geog.CardinalPoint;
import com.marcosavard.commons.geog.Country;
import com.marcosavard.commons.geog.CurrencyGlossary;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.math.arithmetic.PseudoRandom;
import com.marcosavard.commons.math.arithmetic.RomanNumeral;
import com.marcosavard.commons.ui.Affirmation;
import com.marcosavard.commons.ui.Collection;
import com.marcosavard.commons.ui.ColorProperty;
import com.marcosavard.commons.ui.Direction;
import com.marcosavard.commons.ui.FileAttribute;
import com.marcosavard.commons.ui.FileOperation;
import com.marcosavard.commons.ui.WindowOperation;
import com.marcosavard.commons.ui.color.ColorName;

import java.awt.*;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class QuestionList {
  private List<Question> questions = new ArrayList<>();

  public void generateQuestions(Locale display, int seed) {
    generateGuessMonth(display);
    generateGuessMonthAbbreviation(display);
    generateGuessWeekDay(display);
    generateGuessWeekDayAbbreviation(display);
    generateGuessDateAbbreviation(display);
    generateGreekLetter(display);

    generateGuessColor(display);
    generateGuessMetal(display);
    generateGuessMetalByName(display);
    generateGuessChemicalElement(display);

    generateGuessCardinalPointAbbreviation(display);
    generateGuessCardinalPoint(display);

    generateGuessEnumYesNo(display);
    generateGuessEnumDirection(display);
    generateGuessEnumCollection(display);
    generateGuessEnumColorProperty(display);

    generateGuessEnumFileAttribute(display);
    generateGuessEnumFileOperation(display);
    generateGuessEnumWindowOperation(display);

    generateGuessRomanNumerals(display);

    generateGuessDomainByCountryName(display);
    generateGuessCountryByLanguage(display);
    generateGuessLanguageByCountry(display);

    generateGuessCountryByInhabitant(display);
    generateGuessInhabitantByCountry(display);

    generateGuessCountryByCurrency(display);
    generateGuessCurrencyByCountry(display);
    generateGuessCurrencyCodeByName(display);

    // generateTimeZoneCodeByName(display);
    // script, font

    PseudoRandom pr = new PseudoRandom(seed);
    questions = Question.shuffle(questions, pr);
  }

  private void generateGreekLetter(Locale display) {
    char ALPHA = 'Α', OMEGA = 'Ω';

    for (char ch = ALPHA; ch <= OMEGA; ch++) {
      String name = Character.getName(ch);

      if (name != null) {
        name = name.replace("GREEK CAPITAL LETTER", "").trim().toLowerCase();
        Question q = new Question(name, "Lettre grecque");
        questions.add(q);
      }
    }
  }

  private void generateGuessMonth(Locale display) {
    for (Month month : Month.values()) {
      String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, display);
      String hint = "Mois de l'année";
      Question q = new Question(monthName, hint);
      questions.add(q);
    }
  }

  private void generateGuessMonthAbbreviation(Locale display) {
    for (Month month : Month.values()) {
      String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, display);
      String abbreviation = month.getDisplayName(TextStyle.SHORT, display).replace('.', ' ').trim();

      if (monthName.length() > abbreviation.length()) {
        Question q = new Question(abbreviation, monthName);
        questions.add(q);
      }
    }
  }

  private void generateGuessWeekDay(Locale display) {
    DateFormatSymbols symbols = new DateFormatSymbols(display);
    String[] dayNames = symbols.getWeekdays();

    for (String name : dayNames) {
      if (!"".equals(name)) {
        String hint = "Jour de la semaine";
        Question q = new Question(name, hint);
        questions.add(q);
      }
    }
  }

  private void generateGuessWeekDayAbbreviation(Locale display) {
    for (DayOfWeek day : DayOfWeek.values()) {
      String full = day.getDisplayName(TextStyle.FULL_STANDALONE, display);
      String abbreviation = day.getDisplayName(TextStyle.SHORT, display).replace('.', ' ').trim();
      Question q = new Question(abbreviation, full);
      questions.add(q);
    }
  }

  private void generateGuessDateAbbreviation(Locale display) {
    LocalDate date = LocalDate.of(2000, 1, 1);
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("GGGG");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("GG");
    String full = formatter1.format(date);
    String abbr = formatter2.format(date);
    Question q = new Question(abbr, full);
    questions.add(q);
  }

  private void generateGuessColor(Locale display) {
    List<Color> colors = ColorName.getNamedColors();

    for (Color color : colors) {
      String name = ColorName.of(color).toString(display).toLowerCase();

      if (name.indexOf(' ') == -1) {
        Question q = new Question(name, "Couleur");
        questions.add(q);
      }
    }
  }

  private void generateGuessMetal(Locale display) {
    List<String> codes = List.of("XAU", "XAG", "XPD", "XPT");

    for (String code : codes) {
      Currency currency = Currency.getInstance(code);
      String name = currency.getDisplayName(display);
      Question q = new Question(name, "Métal");
      questions.add(q);
    }
  }

  private static void generateGuessMetalByName(Locale display) {
    List<String> codes = List.of("XAU", "XAG", "XPD", "XPT");
    List<Question> questions = new ArrayList<>();

    for (String code : codes) {
      Currency currency = Currency.getInstance(code);
      String name = currency.getDisplayName(display);
      String symbol = code.substring(1);
      Question q = new Question(symbol, name);
      questions.add(q);
    }
  }

  private void generateGuessChemicalElement(Locale display) {
    ChemicalElement[] elements = ChemicalElement.values();

    for (ChemicalElement element : elements) {
      String symbol = element.toString();

      if (symbol.length() >= 2) {
        ChemicalElement.Category category = element.getCategory();
        Question q = null;

        if (category.equals(ChemicalElement.Category.NOBLE_GAS)) {
          q = new Question(symbol, "Gaz noble");
        } else if (category.equals(ChemicalElement.Category.ALKALI)) {
          q = new Question(symbol, "Alcalin");
        } else if (category.equals(ChemicalElement.Category.METAL)) {
          q = new Question(symbol, "Métal");
        }

        if (q != null) {
          questions.add(q);
        }
      }
    }
  }

  private void generateGuessRomanNumerals(Locale display) {
    for (int i = 1; i <= 100; i++) {
      String arabic = Integer.toString(i);
      String roman = RomanNumeral.of(i).toString();

      if (roman.length() >= 2) {
        String hint = MessageFormat.format("{0} en chiffre romain", arabic);
        Question q = new Question(roman, hint);
        questions.add(q);
      }
    }
  }

  private void generateGuessCardinalPointAbbreviation(Locale display) {
    for (CardinalPoint point : CardinalPoint.values()) {
      String full = point.getDisplayName(TextStyle.FULL, display);
      String abbr = point.getDisplayName(TextStyle.SHORT, display);

      if (abbr.length() >= 2) {
        Question q = new Question(abbr, full);
        questions.add(q);
      }
    }
  }

  private void generateGuessCardinalPoint(Locale display) {
    for (CardinalPoint point : CardinalPoint.values()) {
      String full = point.getDisplayName(TextStyle.FULL, display);
      String abbr = point.getDisplayName(TextStyle.SHORT, display);

      if (abbr.length() == 1) {
        Question q = new Question(full, abbr);
        questions.add(q);
      }
    }
  }

  private void generateGuessEnumYesNo(Locale display) {
    questions.add(new Question(Affirmation.YES.getDisplayName(display), "Affirmation"));
    questions.add(new Question(Affirmation.NO.getDisplayName(display), "Negation"));
  }

  private void generateGuessEnumDirection(Locale display) {
    questions.add(new Question(Direction.LEFT.getDisplayName(display), "Côté"));
    questions.add(new Question(Direction.RIGHT.getDisplayName(display), "Côté"));
  }

  private void generateGuessEnumCollection(Locale display) {
    for (Collection item : Collection.values()) {
      Question q = new Question(item.getDisplayName(display), "Collection");
      questions.add(q);
    }
  }

  private void generateGuessEnumColorProperty(Locale display) {
    for (ColorProperty item : ColorProperty.values()) {
      Question q = new Question(item.getDisplayName(display), "Aspect d'une couleur");
      questions.add(q);
    }
  }

  private void generateGuessEnumFileAttribute(Locale display) {
    for (FileAttribute item : FileAttribute.values()) {
      Question q = new Question(item.getDisplayName(display), "Attribut d'un fichier");
      questions.add(q);
    }
  }

  private void generateGuessEnumFileOperation(Locale display) {
    for (FileOperation item : FileOperation.values()) {
      Question q =
          new Question(item.getDisplayName(display), "Operation sur un fichier informatique");
      questions.add(q);
    }
  }

  private void generateGuessEnumWindowOperation(Locale display) {
    for (WindowOperation item : WindowOperation.values()) {
      Question q = new Question(item.getDisplayName(display), "Operation sur une fenetre");
      questions.add(q);
    }
  }

  private void generateGuessDomainByCountryName(Locale display) {
    String[] countries = Locale.getISOCountries();

    for (String code : countries) {
      String countryName = Country.of(code).getDisplayName(display);
      String partitive = findCountryPartitive(code, countryName);
      String hint = MessageFormat.format("Domaine internet {0}{1}", partitive, countryName);
      Question q = new Question(code, hint);
      questions.add(q);
    }
  }

  // le francais y est parle : France
  private void generateGuessCountryByLanguage(Locale display) {
    // languages
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());
    String[] languages = Locale.getISOLanguages();

    for (String language : languages) {
      Locale locale = Locale.forLanguageTag(language);
      String displayLanguage = locale.getDisplayName(display);

      if (displayLanguage.indexOf(' ') == -1) {
        String article = findLanguageArticle(displayLanguage);
        List<Locale> locales =
            allLocales.stream().filter(l -> language.equals(l.getLanguage())).toList();
        List<String> countries =
            locales.stream()
                .filter(l -> !"".equals(l.getCountry()))
                .map(Locale::getCountry)
                .distinct()
                .toList();

        for (String code : countries) {
          Country country = Country.of(code);

          if (country != null) {
            String countryName = country.getDisplayName(display);
            if ((countryName.indexOf(' ') == -1) && (countryName.length() <= 10)) {
              String hint = MessageFormat.format("On y parle {0}{1}", article, displayLanguage);
              Question q = new Question(country.getDisplayName(display), hint);
              questions.add(q);
            }
          }
        }
      }
    }
  }

  // On parle cette langue en France : francais
  private void generateGuessLanguageByCountry(Locale display) {
    // languages
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());
    String[] languages = Locale.getISOLanguages();

    for (String language : languages) {
      Locale locale = Locale.forLanguageTag(language);
      String displayLanguage = locale.getDisplayName(display);

      if (displayLanguage.indexOf(' ') == -1) {
        List<Locale> locales =
            allLocales.stream().filter(l -> language.equals(l.getLanguage())).toList();
        List<String> countries =
            locales.stream()
                .filter(l -> !"".equals(l.getCountry()))
                .map(Locale::getCountry)
                .distinct()
                .toList();

        for (String code : countries) {
          Country country = Country.of(code);

          if (country != null) {
            String countryName = country.getDisplayName(display);
            if ((countryName.indexOf(' ') == -1) && (countryName.length() <= 10)) {
              String determinant = findDeterminant(code, countryName);
              String hint =
                  MessageFormat.format(
                      "On parle cette langue {0} {1}",
                      determinant, country.getDisplayName(display));
              Question q = new Question(displayLanguage, hint);
              questions.add(q);
            }
          }
        }
      }
    }
  }

  // le Francais y habite : France
  private void generateGuessCountryByInhabitant(Locale display) {
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    String[] countries = Locale.getISOCountries();

    for (String countryCode : countries) {
      String[] adjectives = currencyGlossary.getAdjective(countryCode, display);
      if (adjectives[0] != null) {
        String countryName = Country.of(countryCode).getDisplayName(display);

        if (countryName.indexOf(' ') == -1) {
          String adjective = StringUtil.capitalize(adjectives[0]);
          String article = findInabitantArticle(adjective.toLowerCase());
          String hint = MessageFormat.format("{0}{1} y habite", article, adjective);
          Question q = new Question(countryName, hint);
          questions.add(q);
        }
      }
    }
  }

  // il habite en France : Francais
  private void generateGuessInhabitantByCountry(Locale display) {
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    String[] countries = Locale.getISOCountries();

    for (String countryCode : countries) {
      String[] adjective = currencyGlossary.getAdjective(countryCode, display);
      if (adjective[0] != null) {
        String countryName = Country.of(countryCode).getDisplayName(display);
        String article = findCountryArticle(countryCode, countryName.toLowerCase());

        if (!StringUtil.isNullOrEmpty(countryName)) {
          String hint = MessageFormat.format("Il habite {0}{1}", article, countryName);
          Question q = new Question(StringUtil.capitalize(adjective[0]), hint);
          questions.add(q);
        }
      }
    }
  }

  // le rouble y a cours : Russie
  private void generateGuessCountryByCurrency(Locale display) {
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    Locale[] locales = Locale.getAvailableLocales();

    for (Locale locale : locales) {
      String countryCode = locale.getCountry();

      if (countryCode != null) {
        try {
          Currency currency = Currency.getInstance(locale);

          if (currency != null) {
            Country country = Country.of(countryCode);

            if (country != null) {
              String countryName = Country.of(countryCode).getDisplayName(display);
              countryName = countryName.replace('-', ' ');

              if (countryName.indexOf(' ') == -1) {
                String currencyName = currency.getDisplayName(display);
                String[] adjectives = currencyGlossary.getAdjective(countryCode, display);
                adjectives[0] = (adjectives[0] == null) ? "" : adjectives[0];
                adjectives[1] = (adjectives[1] == null) ? "" : adjectives[1];
                currencyName = currencyName.replace(adjectives[1], "").trim();
                currencyName = currencyName.replace(adjectives[0], "").trim();

                String article = StringUtil.capitalize(findCurrencyArticle(currencyName));
                String hint = MessageFormat.format("{0}{1} y a cours", article, currencyName);
                Question q = new Question(countryName, hint);
                questions.add(q);
              }
            }
          }
        } catch (IllegalArgumentException ex) {
          // ignore and continue
        }
      }
    }
  }

  // On utilise cette monnaie en Russie : rouble
  private void generateGuessCurrencyByCountry(Locale display) {
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    Locale[] locales = Locale.getAvailableLocales();

    for (Locale locale : locales) {
      String countryCode = locale.getCountry();

      if (countryCode != null) {
        try {
          Currency currency = Currency.getInstance(locale);

          if (currency != null) {
            Country country = Country.of(countryCode);

            if (country != null) {
              String currencyName = currency.getDisplayName(display);
              String[] adjectives = currencyGlossary.getAdjective(countryCode, display);
              adjectives[0] = (adjectives[0] == null) ? "" : adjectives[0];
              currencyName = currencyName.replace(adjectives[0], "").trim();

              if (currencyName.indexOf(' ') == -1) {
                String countryName = Country.of(countryCode).getDisplayName(display);
                String determinant = findDeterminant(countryCode, countryName);

                String hint =
                    MessageFormat.format(
                        "On utilise cette devise {0} {1}", determinant, countryName);
                Question q = new Question(currencyName, hint);
                questions.add(q);
              }
            }
          }
        } catch (IllegalArgumentException ex) {
          // ignore and continue
        }
      }
    }
  }

  private void generateGuessCurrencyCodeByName(Locale display) {
    Locale[] locales = Locale.getAvailableLocales();

    for (Locale locale : locales) {
      String countryCode = locale.getCountry();

      if (countryCode != null) {
        try {
          Currency currency = Currency.getInstance(locale);

          if (currency != null) {
            String currencyName = currency.getDisplayName(display);
            String code = currency.getCurrencyCode();
            Question q = new Question(code, currencyName);
            questions.add(q);
          }
        } catch (IllegalArgumentException ex) {
          // ignore and continue
        }
      }
    }
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

  private static String findLanguageArticle(String displayLanguage) {
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

  public List<String> getWords() {
    List<String> words = new ArrayList<>();

    for (Question question : questions) {
      String word = StringUtil.stripAccents(question.getWord()).toLowerCase();
      words.add(word);
    }

    return words;
  }

  public List<Question> getQuestions() {
    return questions;
  }
}
