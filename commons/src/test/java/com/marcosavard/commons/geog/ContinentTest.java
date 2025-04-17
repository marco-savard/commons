package com.marcosavard.commons.geog;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;
import junit.framework.Assert;
//import org.junit.Test;

import java.util.Locale;

public class ContinentTest {

 // @Test
  public void testAfricaName() {
    System.out.println();

    testAfricaName("Afrique", Locale.FRENCH);
    testAfricaName("Africa", Locale.ITALIAN);
    testAfricaName("África", Language.SPANISH.toLocale());
    testAfricaName("África", Language.PORTUGUESE.toLocale());
    testAfricaName("Africa", Language.ROMANIAN.toLocale());

    testAfricaName("Africa", Locale.ENGLISH);
    testAfricaName("Afrika", Locale.GERMAN);
    testAfricaName("Afrika", Language.DUTCH.toLocale());
    testAfricaName("Afrika", Language.DANISH.toLocale());
    testAfricaName("Afrika", Language.SWEDISH.toLocale());
    testAfricaName("Afrika", Language.NORVEGIAN.toLocale());
    testAfricaName("Afríka", Language.ICELANDIC.toLocale());

    testAfricaName("Afryka", Language.POLISH.toLocale()); // Afryki R.
    testAfricaName("Afrika", Language.CZECK.toLocale());
    testAfricaName("Afrika", Language.SLOVAK.toLocale());
    testAfricaName("Áfrika", Locale.forLanguageTag("sl")); // Afriška R.
    testAfricaName("Afrika", Language.CROATIAN.toLocale());
    testAfricaName("Afrika", Locale.forLanguageTag("sr-Latn-RS"));

    testAfricaName("Afrika", Language.ALBANIAN.toLocale());
    testAfricaName("Afrikka", Language.FINNISH.toLocale());
    testAfricaName("Afrika", Language.HUNGARIAN.toLocale());
    testAfricaName("Afrika", Language.TURKISH.toLocale());
    testAfricaName("Afrika", Language.AZERBAIJANI.toLocale());

    testAfricaName("Afrika", Language.AFRIKANER.toLocale());
    testAfricaName("Africa", Language.LATIN.toLocale());
    testAfricaName("Afriko", Language.ESPERANTO.toLocale());
  }

 // @Test
  public void testAmericaName() {
    System.out.println();
    testAmericaName("Amérique", Locale.FRENCH);
    testAmericaName("America", Locale.ITALIAN);
    testAmericaName("América", Language.SPANISH.toLocale());
    testAmericaName("América", Language.PORTUGUESE.toLocale());
    testAmericaName("America", Language.ROMANIAN.toLocale());

    testAmericaName("America", Locale.ENGLISH);
    testAmericaName("Amerika", Locale.GERMAN);
    testAmericaName("Amerika", Language.DUTCH.toLocale());
    testAmericaName("Amerika", Language.DANISH.toLocale());
    testAmericaName("Amerika", Language.SWEDISH.toLocale());
    testAmericaName("Amerika", Language.NORVEGIAN.toLocale());
    testAmericaName("Ameríka", Language.ICELANDIC.toLocale());

    testAmericaName("Ameryka", Language.POLISH.toLocale());
    testAmericaName("Amerika", Language.CZECK.toLocale());
    testAmericaName("Amerika", Language.SLOVAK.toLocale());
    testAmericaName("Amêrika", Language.SLOVENIAN.toLocale());
    testAmericaName("Amèrika", Language.CROATIAN.toLocale());
    testAmericaName("Amèrika", Locale.forLanguageTag("sr-Latn-RS"));

    testAmericaName("Amerika", Language.ALBANIAN.toLocale());
    testAmericaName("Amerikka", Language.FINNISH.toLocale());
    testAmericaName("Amerika", Language.HUNGARIAN.toLocale());
    testAmericaName("Amerika", Language.TURKISH.toLocale());
    testAmericaName("Amerikası", Language.AZERBAIJANI.toLocale());

    testAmericaName("Amerika", Language.AFRIKANER.toLocale());
    testAmericaName("America", Language.LATIN.toLocale());
    testAmericaName("Ameriko", Language.ESPERANTO.toLocale());
  }

 // @Test
  public void testAsiaName() {
    System.out.println();
    testAsiaName("Asie", Locale.FRENCH);
    testAsiaName("Asia", Locale.ITALIAN);
    testAsiaName("Asia", Language.SPANISH.toLocale());
    testAsiaName("Ásia", Language.PORTUGUESE.toLocale());
    testAsiaName("Asia", Language.ROMANIAN.toLocale());

    testAsiaName("Asia", Locale.ENGLISH);
    testAsiaName("Asien", Locale.GERMAN);
    testAsiaName("Azië", Language.DUTCH.toLocale());
    testAsiaName("Asien", Language.DANISH.toLocale());
    testAsiaName("Asien", Language.SWEDISH.toLocale());
    testAsiaName("Asia", Language.NORVEGIAN.toLocale());
    testAsiaName("Asía", Language.ICELANDIC.toLocale());

    testAsiaName("Azja", Language.POLISH.toLocale());
    testAsiaName("Asie", Language.CZECK.toLocale());
    testAsiaName("Ázia", Language.SLOVAK.toLocale());
    testAsiaName("Ázija", Language.SLOVENIAN.toLocale());
    testAsiaName("Ȃzija", Language.CROATIAN.toLocale());
    testAsiaName("Ȃzija", Locale.forLanguageTag("sr-Latn-RS"));

    testAsiaName("Azia", Language.ALBANIAN.toLocale());
    testAsiaName("Aasia", Language.FINNISH.toLocale());
    testAsiaName("Ázsia", Language.HUNGARIAN.toLocale());
    testAsiaName("Asya", Language.TURKISH.toLocale());
    testAsiaName("Asiya", Language.AZERBAIJANI.toLocale());

    testAsiaName("Asië", Language.AFRIKANER.toLocale());
    testAsiaName("Asia", Language.LATIN.toLocale());
    testAsiaName("Azio", Language.ESPERANTO.toLocale());
  }

  //@Test
  public void testEuropeName() {
    System.out.println();
    testEuropeName("Europe", Locale.FRENCH);
    testEuropeName("Europa", Locale.ITALIAN);
    testEuropeName("Europa", Language.SPANISH.toLocale());
    testEuropeName("Europa", Language.PORTUGUESE.toLocale());
    testEuropeName("Europa", Language.ROMANIAN.toLocale());

    testEuropeName("Europe", Locale.ENGLISH);
    testEuropeName("Europa", Locale.GERMAN);
    testEuropeName("Europa", Language.DUTCH.toLocale());
    testEuropeName("Europa", Language.DANISH.toLocale());
    testEuropeName("Europa", Language.SWEDISH.toLocale());
    testEuropeName("Europa", Language.NORVEGIAN.toLocale());
    testEuropeName("Evrópa", Language.ICELANDIC.toLocale());

    testEuropeName("Europa", Language.POLISH.toLocale());
    testEuropeName("Evropa", Language.CZECK.toLocale());
    testEuropeName("Európa", Language.SLOVAK.toLocale());
    testEuropeName("Evropa", Language.SLOVENIAN.toLocale());
    testEuropeName("Europa", Language.CROATIAN.toLocale());
    testEuropeName("Evropa", Locale.forLanguageTag("sr-Latn-RS"));

    testEuropeName("Evropë", Language.ALBANIAN.toLocale());
    testEuropeName("Európa", Language.HUNGARIAN.toLocale());
    testEuropeName("Eurooppa", Language.FINNISH.toLocale());
    testEuropeName("Avrupa", Language.TURKISH.toLocale());
    testEuropeName("Avropa", Language.AZERBAIJANI.toLocale());
  }

  private void testAfricaName(String expected, Locale language) {
    String display = Continent.AFRICA.getDisplayName(language);
    Console.println(language.getDisplayLanguage() + " : " + display);
    Assert.assertEquals(expected, display);
  }

  private void testAmericaName(String expected, Locale language) {
    String display = Continent.AMERICA.getDisplayName(language);
    Console.println(language.getDisplayLanguage() + " : " + display);
    Assert.assertEquals(expected, display);
  }

  private void testAsiaName(String expected, Locale language) {
    String display = Continent.ASIA.getDisplayName(language);
    Console.println(language.getDisplayLanguage() + " : " + display);
    Assert.assertEquals(expected, display);
  }

  private void testEuropeName(String expected, Locale language) {
    String display = Continent.EUROPE.getDisplayName(language);
    Console.println(language.getDisplayLanguage() + " : " + display);
    Assert.assertEquals(expected, display);
  }
}
