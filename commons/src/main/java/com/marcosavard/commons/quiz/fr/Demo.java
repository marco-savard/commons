package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.ling.Language;
import com.marcosavard.commons.text.WordUtil;
import com.marcosavard.commons.time.ChronoUnitName;

import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.HijrahEra;
import java.time.chrono.IsoEra;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Demo {

  public static void main(String[] args) {
    Locale display = Language.ENGLISH.toLocale();
    //  timeUnit(display);
    chronology(display);

    Chronology chronology1 = Chronology.of("Japanese");
    Chronology chronology2 = Chronology.of("ThaiBuddhist");
    Chronology chronology3 = Chronology.of("Hijrah-umalqura");

    String fullName1 = chronology1.getDisplayName(TextStyle.FULL, display);
    String fullName2 = chronology2.getDisplayName(TextStyle.FULL, display);
    String fullName3 = chronology3.getDisplayName(TextStyle.FULL, display);

    String calendar = WordUtil.findLonguestCommonWord(fullName1, fullName2);
    String buddhist = WordUtil.removeSubstring(fullName2, calendar).trim();
    String muslim = WordUtil.removeParenthesis(fullName3);
    muslim = WordUtil.removeSubstring(muslim, calendar).trim();

    Console.println(muslim);
    Console.println(calendar);
    Console.println(buddhist);

    String bce = IsoEra.BCE.getDisplayName(TextStyle.FULL, display);
    String ce = IsoEra.CE.getDisplayName(TextStyle.FULL, display);
    String jc = WordUtil.findLonguestCommonWord(bce, ce);
    String before = WordUtil.removeSubstring(bce, jc).trim();
    String after = WordUtil.removeSubstring(ce, jc).trim();

    String era = ChronoField.ERA.getDisplayName(display);
    String ahEra = HijrahEra.AH.getDisplayName(TextStyle.FULL, display);
    String ah = WordUtil.removeSubstring(ahEra, era).trim();
    ah = WordUtil.removeShortWords(ah.replace('’', ' '), 3);
    Console.println(ah);

    Console.println(before);
    Console.println(after);
  }

  private static void chronology(Locale display) {
    List<Chronology> chronologies = new ArrayList<>();
    chronologies.addAll(Chronology.getAvailableChronologies());

    for (Chronology chronology : chronologies) {
      String id = chronology.getId();
      String fullName = chronology.getDisplayName(TextStyle.FULL, display);
      Console.println(id + "/" + fullName);
      List<Era> eras = chronology.eras();

      for (Era era : eras) {
        Console.println("  " + era.getDisplayName(TextStyle.FULL, display));
      }
      Console.println();
    }
  }

  private static void timeUnit(Locale display) {
    for (ChronoUnit cu : ChronoUnit.values()) {
      String s = ChronoUnitName.of(cu).getDisplayName(display);
      Console.println(s);
    }
  }
}
