package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.astro.zodiac.ZodiacSign;
import com.marcosavard.commons.chem.ChemicalElement;
import com.marcosavard.commons.geog.CardinalPoint;
import com.marcosavard.commons.geog.Continent;
import com.marcosavard.commons.geog.Country;
import com.marcosavard.commons.geog.CurrencyGlossary;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.math.arithmetic.PseudoRandom;
import com.marcosavard.commons.math.arithmetic.RomanNumeral;
import com.marcosavard.commons.text.Script;
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

    // antiquite
    generateGreekLetter(display);
    generateGreekGods(display);
    generateRomanGod(display);
    generateAncientScriptName(display);
    generateRomanEmpireCountries(display);
    generateRomanceLanguages(display);
    generateZodiacSigns(display);
    generateGuessRomanNumerals(display);

    /*
    // europe
    generateItMeals(display);
    generateFrMeals(display);
    generateChMeals(display);
    generateGuessCountryByLanguage(Continent.EUROPE, display);
    generateGuessLanguageByCountry(Continent.EUROPE, display);
    generateGuessCountryByInhabitant(Continent.EUROPE, display);
    generateGuessInhabitantByCountry(Continent.EUROPE, display);
    generateGuessCountryByCurrency(Continent.EUROPE, display);
    generateGuessCurrencyByCountry(Continent.EUROPE, display);
    generateGuessCurrencyCodeByName(Continent.EUROPE, display);
    generateGuessDomainByCountryName(Continent.EUROPE, display);
     */

    /*
        //asia
        generateAsianClothes(display);
        generateJpMeals(display);
        generateGuessCountryByLanguage(Continent.ASIA, display);
        generateGuessLanguageByCountry(Continent.ASIA, display);
        generateGuessCountryByInhabitant(Continent.ASIA, display);
        generateGuessInhabitantByCountry(Continent.ASIA, display);
        generateGuessCountryByCurrency(Continent.ASIA, display);
        generateGuessCurrencyByCountry(Continent.ASIA, display);
        generateGuessCurrencyCodeByName(Continent.ASIA, display);
        generateGuessDomainByCountryName(Continent.ASIA, display);
    */

    // america
    /*
        generateMxMeals(display);
        generateUsMeals(display);
        generateUsClothes(display);
        generateGuessCountryByLanguage(Continent.AMERICA, display);
        generateGuessLanguageByCountry(Continent.AMERICA, display);
        generateGuessCountryByInhabitant(Continent.AMERICA, display);
        generateGuessInhabitantByCountry(Continent.AMERICA, display);
        generateGuessCountryByCurrency(Continent.AMERICA, display);
        generateGuessCurrencyByCountry(Continent.AMERICA, display);
        generateGuessCurrencyCodeByName(Continent.AMERICA, display);
        generateGuessDomainByCountryName(Continent.AMERICA, display);
    */

    /*
        // science et techno
        generatePlanet(display);
        generateDwarfPlanet(display);
        generateGuessMetal(display);
        generateGuessMetalByName(display);
        generateGuessEnumColorProperty(display);
        generateGuessEnumFileAttribute(display);
        generateGuessEnumFileOperation(display);
        generateGuessEnumWindowOperation(display);
        generateGuessChemicalElement(display);
        generateGuessDomainByCountryName(Continent.EUROPE, display);
    */
    // general
    generateGuessEnumYesNo(display);
    generateGuessEnumDirection(display);
    generateGuessEnumCollection(display);
    generateGuessCardinalPointAbbreviation(display);
    generateGuessCardinalPoint(display);
    generateGuessWeekDay(display);
    generateGuessWeekDayAbbreviation(display);
    generateGuessDateAbbreviation(display);
    generateGuessMonth(display);
    generateGuessMonthAbbreviation(display);
    generateGuessColor(display);

    //  generateSports(display);
    //  generateRailTransportation(display);
    //  generateRoadTransportation(display);
    //   generateWaterTransportation(display);

    // generateTimeZoneCodeByName(display);
    // script, font

    PseudoRandom pr = new PseudoRandom(seed);
    questions = Question.shuffle(questions, pr);
  }

  private void generateGreekLetter(Locale display) {
    char ALPHA = 'Α', OMEGA = 'Ω';

    for (char ch = ALPHA; ch <= OMEGA; ch++) {
      String name = Character.getName(ch);
      boolean vowel = "ΑΕΙΟΥΩ".indexOf(ch) != -1;

      if (name != null) {
        name = name.replace("GREEK CAPITAL LETTER", "").trim().toLowerCase();
        String hint = vowel ? "Voyelle grecque" : "Consomne grecque";
        Question q = new Question(name, hint);
        questions.add(q);
      }
    }
  }

  private void generateRomanGod(Locale display) {
    char MERCURY = '\u263F', MARS = '\u2642', JUPITER = '\u2643';
    char SATURN = '\u2644', URANUS = '\u2645', NEPTUNE = '\u2646', PLUTO = '\u2647';
    char[] gods = new char[] {MERCURY, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO};

    for (char god : gods) {
      String name = Character.getName(god);
      name = (god == MARS) ? "MARS" : name;

      if (isFrench(display)) {
        name = (god == MERCURY) ? "MERCURE" : name;
        name = (god == SATURN) ? "SATURNE" : name;
        name = (god == PLUTO) ? "PLUTON" : name;
      }

      addQuestion("Dieu romain", name, display);
    }
  }

  private void generateAncientScriptName(Locale display) {
    String hint = "Ancien systeme d'ecriture";
    List<Script> scripts = Script.getAncientScripts();

    for (Script script : scripts) {
      String name = script.getDisplayName(display);
      addQuestion(hint, name, display);
    }
  }

  private void generateRomanEmpireCountries(Locale display) {
    String hint = "Les Romains ont conquis ce pays";
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());
    List<String> romanCountries = List.of("CY", "EG", "ES", "GR", "LY");
    List<Locale> countries =
        allLocales.stream().filter(l -> romanCountries.contains(l.getCountry())).toList();

    for (Locale country : countries) {
      String name = country.getDisplayCountry(display);
      addQuestion(hint, name, display);
    }
  }

  private void generateZodiacSigns(Locale display) {
    String hint = "Signe du Zodiac";

    for (ZodiacSign sign : ZodiacSign.values()) {
      addQuestion(hint, sign.getDisplayName(display), display);
    }
  }

  private void generateRomanceLanguages(Locale display) {
    String hint = "Langue issue du latin";
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());
    List<String> allLanguages = List.of(Locale.getISOLanguages());

    List<String> romanceLanguages =
        List.of("an", "ca", "co", "es", "fr", "gl", "it", "oc", "pt", "rm", "ro", "sc");
    //  List<Locale> languages =
    //     allLocales.stream().filter(l -> romanceLanguages.contains(l.getLanguage())).toList();

    List<String> languages =
        allLanguages.stream().filter(l -> romanceLanguages.contains(l)).toList();

    for (String language : languages) {
      Locale locale = Locale.forLanguageTag(language);
      String name = locale.getDisplayLanguage(display);
      addQuestion(hint, name, display);
    }
  }

  private void generatePlanet(Locale display) {
    char MERCURY = '\u263F', VENUS = '\u2640', EARTH = '\u2641', MARS = '\u2642';
    char SATURN = '\u2644', NEPTUNE = '\u2646';

    for (char ch = MERCURY; ch <= NEPTUNE; ch++) {
      String name = Character.getName(ch);
      name = (ch == VENUS) ? "VENUS" : name;
      name = (ch == MARS) ? "MARS" : name;

      if (isFrench(display)) {
        name = (ch == MERCURY) ? "MERCURE" : name;
        name = (ch == EARTH) ? "TERRE" : name;
        name = (ch == SATURN) ? "SATURNE" : name;
      }

      addQuestion("Planete", name, display);
    }
  }

  private void generateDwarfPlanet(Locale display) {
    char PLUTO = '\u2647', CERES = '\u26b3', PALLAS = '\u26b4';
    char JUNO = '\u26b5', VESTA = '\u26b6', CHIRON = '\u26b7';
    char[] dwartPlanets = new char[] {PLUTO, CERES, PALLAS, JUNO, VESTA, CHIRON};

    for (char ch : dwartPlanets) {
      String name = Character.getName(ch);

      if (isFrench(display)) {
        name = (ch == PLUTO) ? "PLUTON" : name;
      }

      addQuestion("Astre du systeme solaire", name, display);
    }
  }

  private void generateSports(Locale display) {
    String hint = "Sport";

    addQuestion(hint, getName(0x26bd, "S"), display); // soccer
    addQuestion(hint, getName(0x1f3be, "T"), display); // tennis
    addQuestion(hint, getName(0x1f93e, "H"), display); // handball de
    addQuestion(hint, getName(0x1f3c9, "R"), display); // rugby

    addQuestion(hint, getName(0x26be, "B"), display); // baseball usa
    addQuestion(hint, getName(0x1f94e, "S"), display); // softball usa
    addQuestion(hint, getName(0x1f3c8, "F"), display); // football usa
    addQuestion(hint, getName(0x1f3c0, "B"), display); // basketball usa
    addQuestion(hint, getName(0x1f3d2, "H"), display); // canada finlande
    addQuestion(hint, getName(0x1f94c, "C"), display); // curling canada uk

    addQuestion(hint, getName(0x1f3f8, "B"), display); // badmington
    addQuestion(hint, getName(0x1f3d0, "V"), display); // volleyball
  }

  private void generateUsMeals(Locale display) {
    String hint = "On en mange aux USA";
    addQuestion(hint, getName(0x1f354, "H"), display); // hamburger
    addQuestion(hint, getName(0x1f37f, "P"), display); // popcorn
    addQuestion(hint, getName(0x1f96f, "B"), display); // bagel
    addQuestion(hint, getName(0x1f95e, "P"), display); // pancakes
    addQuestion(hint, getName(0x1f96a, "S"), display); // sandwich

    String hotdog = Character.getName(0x1f32d).replace(" ", "").toLowerCase();
    addQuestion(hint, hotdog, display);
  }

  private void generateMxMeals(Locale display) {
    String hint = "On en mange au Mexique";
    addQuestion(hint, getName(0x1f32e, "T"), display); // taco
    addQuestion(hint, getName(0x1f32f, "B"), display); // burrito
    addQuestion(hint, getName(0x1fad4, "T"), display); // tamale
  }

  private void generateItMeals(Locale display) {
    String hint = "On en mange en Italie";

    addQuestion(hint, getName(0x1f35d, "S"), display); // spaghetti
    addQuestion(hint, getName(0x1f355, "P"), display); // pizza
  }

  private void generateJpMeals(Locale display) {
    String hint = "On en mange au Japon";
    addQuestion(hint, getName(0x1f363, "S"), display); // sushi
  }

  private void generateFrMeals(Locale display) {
    String hint = "On en mange en France";
    addQuestion(hint, getName(0x1f950, "C"), display); // croissant
    addQuestion(hint, getName(0x1f956, "BA"), display); // baguette
  }

  private void generateChMeals(Locale display) {
    String hint = "On en mange en Suisse";
    addQuestion(hint, getName(0x1fad5, "F"), display); // fondue
  }

  private void generateRailTransportation(Locale display) {
    String hint = "Circule sur rail";
    addQuestion(hint, getName(0x1f686, "T"), display); // train
    addQuestion(hint, getName(0x1f687, "M"), display); // metro
    addQuestion(hint, getName(0x1f68a, "T"), display); // tram
    addQuestion(hint, getName(0x1f69d, "M"), display); // monorail
  }

  private void generateRoadTransportation(Locale display) {
    String hint = "Circule sur la route";
    addQuestion(hint, getName(0x1f68c, "B"), display); // bus
    addQuestion(hint, getName(0x1f68e, "T"), display); // trolleybus
    addQuestion(hint, getName(0x1f690, "M"), display); // minibus
    addQuestion(hint, getName(0x1f691, "A"), display); // ambulance
    addQuestion(hint, getName(0x1f695, "T"), display); // taxi
    addQuestion(hint, getName(0x1f697, "A"), display); // automobile
    addQuestion(hint, getName(0x1f6f4, "S"), display); // scooter
  }

  private void generateWaterTransportation(Locale display) {
    String hint = "Flotte sur l'eau";
    addQuestion(hint, getName(0x1f6f6, "C"), display); // canoe
    addQuestion(hint, getName(0x26f4, "F"), display); // ferry
  }

  private void generateAsianClothes(Locale display) {
    String hint = "Les orientales en portent";
    addQuestion(hint, getName(0x1f97b, "S"), display); // sari
    addQuestion(hint, getName(0x1f458, "K"), display); // kimono
  }

  private void generateUsClothes(Locale display) {
    String hint = "Les Americains en portent";
    addQuestion(hint, getName(0x1fa73, "S"), display); // shorts
    addQuestion(hint, getName(0x1f456, "J"), display); // jeans
  }

  private void generateGreekGods(Locale display) {
    String hint = "Dieu grec";
    addQuestion(hint, getName(0x2be1, "H"), display); // hades
    addQuestion(hint, getName(0x2be2, "Z"), display); // zeus
    addQuestion(hint, getName(0x2be3, "K"), display); // kronos
    addQuestion(hint, getName(0x2be4, "A"), display); // appolon
    addQuestion(hint, getName(0x2be7, "P"), display); // poseidon
  }

  // ref https://www.fileformat.info/info/unicode/category/So/list.htm

  // U+1F459	BIKINI

  private String getName(int ch, String start) {
    String characterName = Character.getName(ch);
    List<String> words = List.of(characterName.split("\\s+"));
    words = words.stream().filter(s -> s.startsWith(start)).toList();
    String name = String.join(" ", words).toLowerCase();
    return name;
  }

  // meal international
  /*
    U+1F32D	HOT DOG
  U+1F32E	TACO
  U+1F32F	BURRITO
  U+1F354	HAMBURGER
  U+1F35D	SPAGHETTI
  U+1F363	SUSHI
  U+1F37F	POPCORN
     */

  /*
  U+1F458	KIMONO
  U+1F459	BIKINI
   */

  private void addQuestion(String hint, String name, Locale display) {
    name = name.toLowerCase();
    Question q = new Question(name, hint);
    questions.add(q);
  }

  private boolean isFrench(Locale display) {
    return "fr".equals(display.getLanguage());
  }

  private void generateGuessMonth(Locale display) {
    String[] seasonText = new String[] {"d'hiver", "du printemps", "d'été", "d'automne"};

    for (Month month : Month.values()) {
      String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, display);
      int value = month.getValue();
      int idx = (value == 12) ? 0 : value;
      int season = (int) (Math.floor(idx / 3));
      String hint = "Mois " + seasonText[season];
      Question q = new Question(monthName, hint);
      questions.add(q);

      int nb = month.length(false);
      hint = MessageFormat.format("Mois de {0} jours", nb);
      q = new Question(monthName, hint);
      questions.add(q);
    }
  }

  private void generateGuessMonthAbbreviation(Locale display) {
    for (Month month : Month.values()) {
      String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, display);
      String hint = monthName + " abrégé";
      String abbreviation = month.getDisplayName(TextStyle.SHORT, display).replace('.', ' ').trim();

      if (monthName.length() > abbreviation.length()) {
        Question q = new Question(abbreviation, hint);
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
      String hint = full + " abrégé";
      String abbreviation = day.getDisplayName(TextStyle.SHORT, display).replace('.', ' ').trim();
      Question q = new Question(abbreviation, hint);
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
      Question q =
          new Question(item.getDisplayName(display), "Operation sur une fenetre informatique");
      questions.add(q);
    }
  }

  private void generateGuessDomainByCountryName(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    String[] countries = Locale.getISOCountries();

    for (String code : countries) {
      if (continentCountries.contains(code)) {
        String countryName = Country.of(code).getDisplayName(display);
        String partitive = findCountryPartitive(code, countryName);
        String hint = MessageFormat.format("Domaine internet {0}{1}", partitive, countryName);
        Question q = new Question(code, hint);
        questions.add(q);
      }
    }
  }

  // le francais y est parle : France
  private void generateGuessCountryByLanguage(Continent continent, Locale display) {
    // languages
    List<String> continentCountries = continent.getCountries();
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
          if (continentCountries.contains(code)) {
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
  }

  // On parle cette langue en France : francais
  private void generateGuessLanguageByCountry(Continent continent, Locale display) {
    // languages
    List<String> continentCountries = continent.getCountries();
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
          if (continentCountries.contains(code)) {
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
  }

  // le Francais y habite : France
  private void generateGuessCountryByInhabitant(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    String[] countries = Locale.getISOCountries();

    for (String countryCode : countries) {
      if (continentCountries.contains(countryCode)) {
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
  }

  // il habite en France : Francais
  private void generateGuessInhabitantByCountry(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    String[] countries = Locale.getISOCountries();

    for (String countryCode : countries) {
      if (continentCountries.contains(countryCode)) {
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
  }

  // le rouble y a cours : Russie
  private void generateGuessCountryByCurrency(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    Locale[] locales = Locale.getAvailableLocales();

    for (Locale locale : locales) {
      String countryCode = locale.getCountry();

      if (countryCode != null) {
        if (continentCountries.contains(countryCode)) {
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
  }

  // On utilise cette monnaie en Russie : rouble
  private void generateGuessCurrencyByCountry(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    Locale[] locales = Locale.getAvailableLocales();

    for (Locale locale : locales) {
      String countryCode = locale.getCountry();

      if (countryCode != null) {
        if (continentCountries.contains(countryCode)) {
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
  }

  private void generateGuessCurrencyCodeByName(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    Locale[] locales = Locale.getAvailableLocales();

    for (Locale locale : locales) {
      String countryCode = locale.getCountry();

      if (countryCode != null) {
        if (continentCountries.contains(countryCode)) {
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
