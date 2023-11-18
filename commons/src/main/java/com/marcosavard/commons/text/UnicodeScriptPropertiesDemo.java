package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.lang.IndexSafe;
import com.marcosavard.commons.lang.NullSafe;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UnicodeScriptPropertiesDemo {

  public static void main(String[] args) {
    Locale displayLocale = Locale.FRENCH;
    printAllScriptProperties(displayLocale);
    printScriptByCategories(displayLocale);
    // printUnicodeScriptRange();
  }

  private static void printScriptByCategories(Locale displayLocale) {
    List<Character.UnicodeScript> allScripts = List.of(Character.UnicodeScript.values());
    List<String> allLanguages = List.of(Locale.getISOLanguages());
    UnicodeScriptProperties scriptProperties = new UnicodeScriptProperties();

    List<Character.UnicodeScript> scripts = new ArrayList<>();
    scripts.addAll(
        scriptProperties.findScriptsByDirectionality(Character.DIRECTIONALITY_RIGHT_TO_LEFT));
    scripts.addAll(
        scriptProperties.findScriptsByDirectionality(
            Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC));

    Console.println("LTR scripts");
    for (Character.UnicodeScript script : scripts) {
      String name = scriptProperties.getDisplayName(script, displayLocale);
      Console.println("  " + name);
    }
    Console.println();

    List<String> exclude = List.of("ja", "zh");
    for (Character.UnicodeScript script : allScripts) {
      List<String> languageList =
          scriptProperties.getLanguagesForScript(allLanguages, script, displayLocale);

      if (languageList.size() < 15) {
        String scriptName = scriptProperties.getDisplayName(script, displayLocale);
        for (String language : languageList) {
          if (!exclude.contains(language)) {
            String languageName = Locale.forLanguageTag(language).getDisplayName(displayLocale);

            if (scriptName.equals(languageName)) {
              Console.println("  cette langue a son propre ecriture : " + languageName);
            } else {
              Console.println("  le {0} utilise cette ecriture : {1}", languageName, scriptName);
            }
          }
        }
      }
    }
    Console.println();

    Console.println("American scripts");
    scripts = scriptProperties.getAmericanScripts();
    for (Character.UnicodeScript script : scripts) {
      String name = scriptProperties.getDisplayName(script, displayLocale);
      Console.println("  " + name);
    }

    Console.println("Ancient scripts");
    scripts = scriptProperties.getAncientScripts();
    for (Character.UnicodeScript script : scripts) {
      String name = scriptProperties.getDisplayName(script, displayLocale);
      Console.println("  " + name);
    }

    Console.println("Japanese scripts");
    scripts = scriptProperties.getJapaneseScripts();
    for (Character.UnicodeScript script : scripts) {
      String name = scriptProperties.getDisplayName(script, displayLocale);
      Console.println("  " + name);
    }
  }

  private static void printAllScriptProperties(Locale displayLocale) {
    UnicodeScriptProperties scriptProperties = new UnicodeScriptProperties();
    List<String> allLanguages = List.of(Locale.getISOLanguages());
    int count = Character.UnicodeScript.values().length;

    for (int i = 1; i < count; i++) {
      Character.UnicodeScript script = Character.UnicodeScript.values()[i];
      String displayName = scriptProperties.getDisplayName(script, displayLocale);
      String nativeName = scriptProperties.getNativeName(script);
      byte directionality = scriptProperties.getDirectionality(script);
      String direction = Byte.toString(directionality);
      List<String> languageList =
          scriptProperties.getLanguagesForScript(allLanguages, script, displayLocale);
      String languages = String.join(",", languageList);
      List<int[]> ranges = scriptProperties.getScriptRanges(script);

      String joined =
          String.join(", ", displayName, direction, nativeName, languages, toString(ranges));
      Console.println("{0}) {1} : {2}", i, script.name(), joined);
    }
  }

  private static String toString(List<int[]> ranges) {
    List<String> items = new ArrayList<>();

    for (int[] range : ranges) {
      items.add(toString(range));
    }

    String str = "[" + String.join(", ", items) + "]";
    return str;
  }

  private static String toString(int[] range) {
    int start = range[0];
    int end = range[range.length - 1];
    String span =
        (start == end)
            ? Character.toString(start)
            : Character.toString(start) + ".." + Character.toString(end);
    String str = "(" + span + ")";
    return str;
  }

  private static void printUnicodeScriptRange() {
    UnicodeScriptProperties scriptProperties = new UnicodeScriptProperties();
    List<Character.UnicodeScript> scripts = scriptProperties.getScriptList();
    List<Integer> scriptStarts = scriptProperties.getScriptStartList();

    for (int i = 0; i < scripts.size(); i++) {
      int scriptStart = scriptStarts.get(i);
      Integer scriptEnd = IndexSafe.get(scriptStarts, i + 1);
      Character.UnicodeScript script = scripts.get(i);
      String hexa = String.format("0x%04X", scriptStart);
      char ch1 = (char) scriptStart;
      char ch2 = (char) (int) (NullSafe.coalesce(scriptEnd, 0) - 1);

      String msg = MessageFormat.format("{0} {1}-{2} : {3}", hexa, ch1, ch2, script);
      System.out.println(msg);
    }
  }
}
