package com.marcosavard.commons.text;

import com.marcosavard.commons.debug.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScriptDemo {

  public static void main(String[] args) {
    Locale display = Locale.FRENCH;
    // printUnicodeScripts();
    // printScripts(display);
    printScriptsByCategories(display);
  }

  private static void printUnicodeScripts() {
    List<Character.UnicodeScript> allScripts = List.of(Character.UnicodeScript.values());

    for (Character.UnicodeScript script : allScripts) {
      Console.println(script.name());
    }
  }

  private static void printScripts(Locale display) {
    List<Script> allScripts = Script.getAllScripts();
    int count = 1;

    for (Script script : allScripts) {
      Character.UnicodeScript unicodeScript = script.getUnicodeScript();
      String name = unicodeScript.name();
      String displayName = script.getDisplayName(display);
      String nativeName = script.getNativeName();
      byte dir = script.getDirectionality();
      List<String> languageList = script.getLanguages();
      String languages = String.join(",", languageList);
      String ranges = toString(script.getScriptRanges());
      Console.println(
          "{0}) {1} {2} {3} {4} {5} {6}",
          count++, displayName, nativeName, dir, languages, name, ranges);
    }
  }

  private static void printScriptsByCategories(Locale display) {
    Console.println("Ancient scripts");
    List<Script> scripts = Script.getAncientScripts();
    for (Script script : scripts) {
      String name = script.getDisplayName(display);
      Console.println("  " + name);
    }

    Console.println();
    Console.println("Japanese scripts");
    scripts = Script.getJapaneseScripts();
    for (Script script : scripts) {
      String name = script.getDisplayName(display);
      Console.println("  " + name);
    }

    Console.println();
    Console.println("American scripts");
    scripts = Script.getAmericanScripts();
    for (Script script : scripts) {
      String name = script.getDisplayName(display);
      Console.println("  " + name);
    }

    Console.println();
    Console.println("RTL scripts");
    scripts = Script.getRtlScripts();
    for (Script script : scripts) {
      String name = script.getDisplayName(display);
      Console.println("  " + name);
    }

    Console.println();
    Console.println("Script par langue");
    List<Script> allScripts = Script.getAllScripts();
    for (Script script : allScripts) {
      List<String> languages = script.getLanguages();
      if (languages.size() < 15) {
        String scriptName = script.getDisplayName(display);
        for (String language : languages) {
          String languageName = Locale.forLanguageTag(language).getDisplayName(display);

          if (languageName.equals(scriptName)) {
            Console.println("  cette langue a sa propre ecriture : " + languageName);
          } else {
            Console.println("  le {0} utilise cette ecriture : {1}", languageName, scriptName);
          }
        }
      }
    }
  }

  private static String toString(List<int[]> scriptRanges) {
    List<String> ranges = new ArrayList<>();

    for (int[] range : scriptRanges) {
      ranges.add(toString(range));
    }

    return "[" + String.join(", ", ranges) + "]";
  }

  private static String toString(int[] range) {
    return "(" + Integer.toString(range[0]) + "-" + Integer.toString(range[1]) + ")";
  }
}
