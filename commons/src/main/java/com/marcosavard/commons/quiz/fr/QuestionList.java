package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.astro.planet.Planet;
import com.marcosavard.commons.astro.zodiac.ZodiacSign;
import com.marcosavard.commons.astro.zodiac.phonetic.PhoneticLetter;
import com.marcosavard.commons.chem.ChemicalElement;
import com.marcosavard.commons.game.chess.Chess;
import com.marcosavard.commons.game.sport.Sport;
import com.marcosavard.commons.geog.CardinalPoint;
import com.marcosavard.commons.geog.Continent;
import com.marcosavard.commons.geog.CountryOld;
import com.marcosavard.commons.geog.CurrencyGlossary;
import com.marcosavard.commons.geog.TimeZoneGlossary;
import com.marcosavard.commons.geog.ca.CanadianProvince;
import com.marcosavard.commons.geog.us.State;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.ling.Numeral;
import com.marcosavard.commons.math.arithmetic.PseudoRandom;
import com.marcosavard.commons.math.arithmetic.RomanNumeral;
import com.marcosavard.commons.text.Script;
import com.marcosavard.commons.time.TimeUnitName;
import com.marcosavard.commons.ui.Affirmation;
import com.marcosavard.commons.ui.Collection;
import com.marcosavard.commons.ui.ColorProperty;
import com.marcosavard.commons.ui.Direction;
import com.marcosavard.commons.ui.FileAttribute;
import com.marcosavard.commons.ui.FileOperation;
import com.marcosavard.commons.ui.WindowOperation;
import com.marcosavard.commons.ui.color.ColorName;

import java.awt.*;
import java.lang.reflect.Method;
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
import java.util.concurrent.TimeUnit;

public class QuestionList {
  private List<Question> questions = new ArrayList<>();
  private TimeZoneGlossary timeZoneGlossary = new TimeZoneGlossary();

  public void generateQuestions(Locale display, int seed) {

    // antiquite
    PseudoRandom pr = new PseudoRandom(seed);
    generateGreekLetter(display);
    generateGreekGods(display);
    generateRomanGod(display);
    generateAncientScriptName(display);
    generateRomanEmpireCountries(display);
    generateRomanceLanguages(display);
    generateZodiacSigns(display);
    generateChessPieces(display);
    generatePhoneticLetter(display);
    generateGuessRomanNumerals(display);

    // europe
    generateEuropeQuestions(display);
    generateSports(Continent.EUROPE, display);
    generateGeographyQuestions(Continent.EUROPE, display, pr, 1000);

    // america
    generateAmericaQuestions(display);
    generateSports(Continent.AMERICA, display);
    generateUsState(display);
    generateCanadianProvinces(display);
    generateGeographyQuestions(Continent.AMERICA, display, pr, 1000);

    // asia
    generateAsiaQuestions(display);
    generateSports(Continent.ASIA, display);
    generateGeographyQuestions(Continent.ASIA, display, pr, 1000);

    // africa
    generateGeographyQuestions(Continent.AFRICA, display, pr, 1000);

    // oceanie
    generateGeographyQuestions(Continent.AUSTRALIA, display, pr, 1000);

    // science et techno
    generateGuessEnumColorProperty(display);
    generateGuessEnumFileAttribute(display);
    generateGuessEnumFileOperation(display);
    generateGuessEnumWindowOperation(display);
    generatePlanet(display);
    generateNumerals(display);
    generateTimeUnit(display);
    generateMathFunctions(display);
    generateGuessMetal(display);
    generateGuessMetalByName(display);
    generateGuessChemicalElement(display);
    generateFontName(display);

    // general
    generateGuessEnumYesNo(display);
    generateGuessColor(display);
    generateGuessEnumDirection(display);
    generateGuessEnumCollection(display);

    generateGuessCardinalPointAbbreviation(display);
    generateGuessCardinalPoint(display);
    generateGuessWeekDay(display);
    generateGuessWeekDayAbbreviation(display);
    generateGuessDateAbbreviation(display);
    generateGuessMonth(display);
    generateGuessMonthAbbreviation(display);

    generateSports(display);
    generateRailTransportation(display);
    generateRoadTransportation(display);
    generateWaterTransportation(display);
    generateScriptByLanguage(display);

    // generateTimeZoneCodeByName(display);

    questions = Question.shuffle(questions, pr);
  }

  private void generateFontName(Locale display) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Font[] allFonts = ge.getAllFonts();
    List<String> fonts = new ArrayList<>();
    String hint = "Police de carateres";

    for (Font font : allFonts) {
      String fontName = font.getFontName(Locale.FRENCH).toLowerCase();

      if (fontName.contains("gras")) {
        fontName = fontName.replace("microsoft", "");
        fontName = fontName.replace("linotype", "");
        fontName = fontName.replace("italique", "");
        fontName = fontName.replace("roman", "");
        fontName = fontName.replace("gras", "");
        fontName = fontName.replace("sans", "");
        fontName = fontName.replace("new", "");
        fontName = fontName.replace("ui", "");
        fontName = fontName.replace("ms", "").trim();

        if (!fonts.contains(fontName)) {
          fonts.add(fontName);
        }
      }
    }

    for (String font : fonts) {
      addQuestion(hint, font);
    }
  }

  private void generateGeographyQuestions(
      Continent continent, Locale display, PseudoRandom pr, int maximum) {
    List<Question> questions = new ArrayList<>();
    questions.addAll(generateGuessCountryByLanguage(continent, display));
    questions.addAll(generateGuessLanguageByCountry(continent, display));
    questions.addAll(generateGuessCountryByInhabitant(continent, display));
    questions.addAll(generateGuessInhabitantByCountry(continent, display));
    questions.addAll(generateGuessCountryByCurrency(continent, display));
    questions.addAll(generateGuessCurrencyByCountry(continent, display));
    questions.addAll(generateGuessCurrencyCodeByName(continent, display));
    questions.addAll(generateGuessDomainByCountryName(continent, display));
    List<Question> shuffled = pr.shuffle(questions);
    int nb = Math.min(shuffled.size(), maximum);

    for (int i = 0; i < nb; i++) {
      Question question = shuffled.get(i);
      addQuestion(question.getHint(), question.getWord());
    }
  }

  private void generatePhoneticLetter(Locale display) {
    for (PhoneticLetter letter : PhoneticLetter.values()) {
      int rank = 1 + letter.ordinal();
      String hint = rank + "e lettre en alphabet phonetique";
      String word = letter.name().toLowerCase();
      addQuestion(hint, word);
    }
  }

  private void generateScriptByLanguage(Locale display) {
    List<Script> allScripts = Script.getAllScripts();
    for (Script script : allScripts) {
      List<String> languages = script.getLanguages();
      if (languages.size() < 15) {
        String scriptName = script.getDisplayName(display);
        for (String language : languages) {
          String languageName = Locale.forLanguageTag(language).getDisplayName(display);

          if (languageName.equals(scriptName)) {
            addQuestion("Cette langue a sa propre ecriture", languageName);
          } else {
            String hint = MessageFormat.format("le {0} utilise cette ecriture", languageName);
            addQuestion(hint, scriptName);
          }
        }
      }
    }
  }

  private void generateAsiaQuestions(Locale display) {
    generateAsianClothes(display);
    generateJpMeals(display);
    addQuestion("Region d'Asie", CountryOld.localesOf("MO").get(0).getDisplayName(display));
    addQuestion("Region d'Asie", timeZoneGlossary.getIndochina(display));
    addQuestion("Ville de Siberie", timeZoneGlossary.getIrkutsk(display));
    addQuestion("Ville de Siberie", timeZoneGlossary.getKrasnoyarsk(display));
    addQuestion("Ville de Siberie", timeZoneGlossary.getNovosibirsk(display));
    addQuestion("Ville de Siberie", timeZoneGlossary.getSakhalin(display));
    addQuestion("Ville de Siberie", timeZoneGlossary.getOmsk(display));
    addQuestion("Ville de Siberie", timeZoneGlossary.getYakutsk(display));
    addQuestion("Ville de Siberie", timeZoneGlossary.getYekaterinburg(display));
  }

  private void generateEuropeQuestions(Locale display) {
    generateItMeals(display);
    generateFrMeals(display);
    generateChMeals(display);
    addQuestion("Ville de Russie", timeZoneGlossary.getMoscow(display));
    addQuestion("Ville de Russie", timeZoneGlossary.getVolgograd(display));
  }

  private void generateAmericaQuestions(Locale display) {
    generateMxMeals(display);
    generateUsMeals(display);
    generateUsClothes(display);
    addQuestion("Montagnes d'Amerique", timeZoneGlossary.getRockiesWord(display));
    addQuestion("Foret d'Amerique du Sud", timeZoneGlossary.getAmazonia(display));
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

      addQuestion("Dieu romain", name);
    }
  }

  private void generateAncientScriptName(Locale display) {
    String hint = "Ancien systeme d'ecriture";
    List<Script> scripts = Script.getAncientScripts();

    for (Script script : scripts) {
      String name = script.getDisplayName(display);
      addQuestion(hint, name);
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
      addQuestion(hint, name);
    }
  }

  private void generateZodiacSigns(Locale display) {
    String hint = "Signe du Zodiac";

    for (ZodiacSign sign : ZodiacSign.values()) {
      addQuestion(hint, sign.getDisplayName(display));
    }
  }

  private void generateChessPieces(Locale display) {
    String hint = "Aux échecs";

    for (Chess sign : Chess.values()) {
      addQuestion(hint, sign.getDisplayName(display));
    }
  }

  private void generateRomanceLanguages(Locale display) {
    String hint = "Langue issue du latin";
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());
    List<String> allLanguages = List.of(Locale.getISOLanguages());

    List<String> romanceLanguages =
        List.of("an", "ca", "co", "es", "fr", "gl", "it", "oc", "pt", "rm", "ro", "sc");
    List<String> languages =
        allLanguages.stream().filter(l -> romanceLanguages.contains(l)).toList();

    for (String language : languages) {
      Locale locale = Locale.forLanguageTag(language);
      String name = locale.getDisplayLanguage(display);
      addQuestion(hint, name);
    }
  }

  private void generateNumerals(Locale display) {
    Numeral numeral = new Numeral();

    for (int i = 1; i <= 100; i++) {
      String num = numeral.of(i);
      if (!num.contains(" ") && !num.contains("-")) {
        int square = i * i;
        String hint = "Racine carree de " + Integer.toString(square);
        addQuestion(hint, num);
      }
    }
  }

  private void generateTimeUnit(Locale display) {
    addQuestion("ns", TimeUnitName.of(TimeUnit.NANOSECONDS).getDisplayName(display));
    addQuestion("1000 ns", TimeUnitName.of(TimeUnit.MICROSECONDS).getDisplayName(display));
    addQuestion("dans une seconde", TimeUnitName.of(TimeUnit.MILLISECONDS).getDisplayName(display));
    addQuestion("dans une minute", TimeUnitName.of(TimeUnit.SECONDS).getDisplayName(display));
    addQuestion("60 secondes", TimeUnitName.of(TimeUnit.MINUTES).getDisplayName(display));
    addQuestion("60 minutes", TimeUnitName.of(TimeUnit.HOURS).getDisplayName(display));
    addQuestion("24 heures", TimeUnitName.of(TimeUnit.DAYS).getDisplayName(display));
  }

  private void generateMathFunctions(Locale display) {
    Method[] methods = Math.class.getDeclaredMethods();
    List<String> functions = new ArrayList<>();
    String hint = "Fonction mathematique";

    for (Method method : methods) {
      String name = method.getName();
      boolean hasDigit = name.matches(".*\\d.*");
      boolean allLowercase = name.toLowerCase().equals(name);
      boolean unary = (method.getParameterCount() == 1);

      if (unary && allLowercase && !hasDigit && !functions.contains(name)) {
        functions.add(name);
      }
    }

    for (String function : functions) {
      addQuestion(hint, function);
    }
  }

  private void generatePlanet(Locale display) {
    for (Planet planet : Planet.values()) {
      Planet.Category category = planet.getCategory();
      String hint;

      if (category == Planet.Category.OFFICIAL) {
        hint = "Planete";
      } else {
        hint = "Corps celeste";
      }

      addQuestion(hint, planet.getDisplayName(display));
    }
  }

  private void generateCanadianProvinces(Locale display) {
    String hint = "Province canadienne";

    for (CanadianProvince province : CanadianProvince.values()) {
      String name = province.getDisplayName(display);

      if (!name.contains(" ") && !name.contains("-")) {
        addQuestion(hint, name);
      }
    }
  }

  private void generateUsState(Locale display) {
    for (State state : State.values()) {
      String name = state.getDisplayName(display);
      State.Category category = state.getCategory();
      State.Region region = state.getRegion();
      String hint;

      if (category == State.Category.STATE) {
        if (region == State.Region.MIDWEST) {
          hint = "Etat americain du Midwest";
        } else if (region == State.Region.EAST_COAST) {
          hint = "Etat americain de la cote est";
        } else if (region == State.Region.SOUTH) {
          hint = "Etat americain du sud";
        } else if (region == State.Region.SOUTH_WEST) {
          hint = "Etat americain du sud-ouest";
        } else if (region == State.Region.NORTH_WEST) {
          hint = "Etat americain du nord-ouest";
        } else if (region == State.Region.PACIFIC) {
          hint = "Etat americain du Pacifique";
        } else {
          hint = "Etat americain";
        }
      } else if (category == State.Category.DISTRICT) {
        hint = "District americain";
      } else {
        hint = "Territoire americain";
      }

      addQuestion(hint, name);
    }
  }

  private void generateSports(Locale display) {
    generateSports(Continent.AMERICA, display);
    generateSports(Continent.ASIA, display);
    generateSports(Continent.EUROPE, display);
  }

  private void generateSports(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());

    for (Sport sport : Sport.values()) {
      List<String> countries = sport.getCountries();

      for (String country : countries) {
        if (continentCountries.contains(country)) {
          List<Locale> locales =
              allLocales.stream().filter(l -> country.equals(l.getCountry())).toList();
          for (Locale locale : locales) {
            String countryName = locale.getDisplayCountry(display);
            String article = findDeterminant(country, countryName);
            String hint =
                MessageFormat.format(
                    "Sport populaire {0} {1}", article, locale.getDisplayCountry(display));
            addQuestion(hint, sport.getDisplayName(display));
          }
        }
      }
    }
  }

  private void generateUsMeals(Locale display) {
    String hint = "On en mange aux USA";
    addQuestion(hint, getName(0x1f354, "H")); // hamburger
    addQuestion(hint, getName(0x1f37f, "P")); // popcorn
    addQuestion(hint, getName(0x1f96f, "B")); // bagel
    addQuestion(hint, getName(0x1f95e, "P")); // pancakes
    addQuestion(hint, getName(0x1f96a, "S")); // sandwich

    String hotdog = Character.getName(0x1f32d).replace(" ", "").toLowerCase();
    addQuestion(hint, hotdog);
  }

  private void generateMxMeals(Locale display) {
    String hint = "On en mange au Mexique";
    addQuestion(hint, getName(0x1f32e, "T")); // taco
    addQuestion(hint, getName(0x1f32f, "B")); // burrito
    addQuestion(hint, getName(0x1fad4, "T")); // tamale
  }

  private void generateItMeals(Locale display) {
    String hint = "On en mange en Italie";

    addQuestion(hint, getName(0x1f35d, "S")); // spaghetti
    addQuestion(hint, getName(0x1f355, "P")); // pizza
  }

  private void generateJpMeals(Locale display) {
    String hint = "On en mange au Japon";
    addQuestion(hint, getName(0x1f363, "S")); // sushi
  }

  private void generateFrMeals(Locale display) {
    String hint = "On en mange en France";
    addQuestion(hint, getName(0x1f950, "C")); // croissant
    addQuestion(hint, getName(0x1f956, "BA")); // baguette
  }

  private void generateChMeals(Locale display) {
    String hint = "On en mange en Suisse";
    addQuestion(hint, getName(0x1fad5, "F")); // fondue
  }

  private void generateRailTransportation(Locale display) {
    String hint = "Circule sur rail";
    addQuestion(hint, getName(0x1f686, "T")); // train
    addQuestion(hint, getName(0x1f687, "M")); // metro
    addQuestion(hint, getName(0x1f68a, "T")); // tram
    addQuestion(hint, getName(0x1f69d, "M")); // monorail
  }

  private void generateRoadTransportation(Locale display) {
    String hint = "Circule sur la route";
    addQuestion(hint, getName(0x1f68c, "B")); // bus
    addQuestion(hint, getName(0x1f68e, "T")); // trolleybus
    addQuestion(hint, getName(0x1f690, "M")); // minibus
    addQuestion(hint, getName(0x1f691, "A")); // ambulance
    addQuestion(hint, getName(0x1f695, "T")); // taxi
    addQuestion(hint, getName(0x1f697, "A")); // automobile
    addQuestion(hint, getName(0x1f6f4, "S")); // scooter
  }

  private void generateWaterTransportation(Locale display) {
    String hint = "Flotte sur l'eau";
    addQuestion(hint, getName(0x1f6f6, "C")); // canoe
    addQuestion(hint, getName(0x26f4, "F")); // ferry
  }

  private void generateAsianClothes(Locale display) {
    String hint = "Les orientales en portent";
    addQuestion(hint, getName(0x1f97b, "S")); // sari
    addQuestion(hint, getName(0x1f458, "K")); // kimono
  }

  private void generateUsClothes(Locale display) {
    String hint = "Les Americains en portent";
    addQuestion(hint, getName(0x1fa73, "S")); // shorts
    addQuestion(hint, getName(0x1f456, "J")); // jeans
  }

  private void generateGreekGods(Locale display) {
    String hint = "Dieu grec";
    addQuestion(hint, getName(0x2be1, "H")); // hades
    addQuestion(hint, getName(0x2be2, "Z")); // zeus
    addQuestion(hint, getName(0x2be3, "K")); // kronos
    addQuestion(hint, getName(0x2be4, "A")); // appolon
    addQuestion(hint, getName(0x2be7, "P")); // poseidon
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

  private void addQuestion(String hint, String name) {
    if (!name.contains(" ") && !name.contains("-")) {
      name = name.toLowerCase();
      Question q = new Question(name, hint);
      questions.add(q);
    }
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
        String name = element.getDisplayName(display);
        Question q1 = new Question(symbol, name);
        questions.add(q1);

        ChemicalElement.Category category = element.getCategory();
        Question q = null;

        if (category.equals(ChemicalElement.Category.NOBLE_GAS)) {
          q = new Question(name, "Gaz noble");
        } else if (category.equals(ChemicalElement.Category.ALKALI)) {
          q = new Question(name, "Alcalin");
        } else if (category.equals(ChemicalElement.Category.METAL)) {
          q = new Question(name, "Métal");
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
    String hint = "Attribut d'un fichier";

    for (FileAttribute item : FileAttribute.values()) {
      addQuestion(hint, item.getDisplayName(display));
    }
  }

  private void generateGuessEnumFileOperation(Locale display) {
    String hint = "Operation sur un fichier informatique";

    for (FileOperation item : FileOperation.values()) {
      addQuestion(hint, item.getDisplayName(display));
    }
  }

  private void generateGuessEnumWindowOperation(Locale display) {
    String hint = "Operation sur une fenetre informatique";

    for (WindowOperation item : WindowOperation.values()) {
      addQuestion(hint, item.getDisplayName(display));
    }
  }

  private List<Question> generateGuessDomainByCountryName(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    String[] countries = Locale.getISOCountries();
    List<Question> questions = new ArrayList<>();

    for (String code : countries) {
      if (continentCountries.contains(code)) {
        String countryName = CountryOld.of(code).getDisplayName(display);
        String partitive = findCountryPartitive(code, countryName);
        String hint = MessageFormat.format("Domaine internet {0}{1}", partitive, countryName);
        Question question = new Question(code, hint);
        questions.add(question);
      }
    }

    return questions;
  }

  // le francais y est parle : France
  private List<Question> generateGuessCountryByLanguage(Continent continent, Locale display) {
    // languages
    List<String> continentCountries = continent.getCountries();
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());
    String[] languages = Locale.getISOLanguages();
    List<Question> questions = new ArrayList<>();

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
            CountryOld country = CountryOld.of(code);

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

    return questions;
  }

  // On parle cette langue en France : francais
  private List<Question> generateGuessLanguageByCountry(Continent continent, Locale display) {
    // languages
    List<String> continentCountries = continent.getCountries();
    List<Locale> allLocales = List.of(Locale.getAvailableLocales());
    String[] languages = Locale.getISOLanguages();
    List<Question> questions = new ArrayList<>();

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
            CountryOld country = CountryOld.of(code);

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

    return questions;
  }

  // le Francais y habite : France
  private List<Question> generateGuessCountryByInhabitant(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    String[] countries = Locale.getISOCountries();
    List<Question> questions = new ArrayList<>();

    for (String countryCode : countries) {
      if (continentCountries.contains(countryCode)) {
        String[] adjectives = currencyGlossary.getAdjective(countryCode, display);
        if (adjectives[0] != null) {
          String countryName = CountryOld.of(countryCode).getDisplayName(display);

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

    return questions;
  }

  // il habite en France : Francais
  private List<Question> generateGuessInhabitantByCountry(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    String[] countries = Locale.getISOCountries();
    List<Question> questions = new ArrayList<>();

    for (String countryCode : countries) {
      if (continentCountries.contains(countryCode)) {
        String[] adjective = currencyGlossary.getAdjective(countryCode, display);
        if (adjective[0] != null) {
          String countryName = CountryOld.of(countryCode).getDisplayNameWithArticle(display);
          String article = findCountryArticle(countryCode, countryName.toLowerCase());

          if (!StringUtil.isNullOrEmpty(countryName)) {
            String hint = MessageFormat.format("Il habite {0}", countryName);
            Question q = new Question(adjective[0], hint);
            questions.add(q);
          }
        }
      }
    }

    return questions;
  }

  // le rouble y a cours : Russie
  private List<Question> generateGuessCountryByCurrency(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    Locale[] locales = Locale.getAvailableLocales();
    List<Question> questions = new ArrayList<>();

    for (Locale locale : locales) {
      String countryCode = locale.getCountry();

      if (countryCode != null) {
        if (continentCountries.contains(countryCode)) {
          try {
            Currency currency = Currency.getInstance(locale);

            if (currency != null) {
              CountryOld country = CountryOld.of(countryCode);

              if (country != null) {

                String countryName = CountryOld.of(countryCode).getDisplayName(display);
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

    return questions;
  }

  // On utilise cette monnaie en Russie : rouble
  private List<Question> generateGuessCurrencyByCountry(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    CurrencyGlossary currencyGlossary = CurrencyGlossary.of(display);
    Locale[] locales = Locale.getAvailableLocales();
    List<Question> questions = new ArrayList<>();

    for (Locale locale : locales) {
      String countryCode = locale.getCountry();

      if (countryCode != null) {
        if (continentCountries.contains(countryCode)) {
          try {
            Currency currency = Currency.getInstance(locale);

            if (currency != null) {
              CountryOld country = CountryOld.of(countryCode);

              if (country != null) {
                String currencyName = currency.getDisplayName(display);
                String[] adjectives = currencyGlossary.getAdjective(countryCode, display);
                adjectives[0] = (adjectives[0] == null) ? "" : adjectives[0];
                currencyName = currencyName.replace(adjectives[0], "").trim();

                if (currencyName.indexOf(' ') == -1) {
                  String countryName = CountryOld.of(countryCode).getDisplayName(display);
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

    return questions;
  }

  private List<Question> generateGuessCurrencyCodeByName(Continent continent, Locale display) {
    List<String> continentCountries = continent.getCountries();
    Locale[] locales = Locale.getAvailableLocales();
    List<Question> questions = new ArrayList<>();

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

    return questions;
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
