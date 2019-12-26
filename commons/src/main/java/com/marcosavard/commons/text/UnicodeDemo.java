package com.marcosavard.commons.text;

import java.text.MessageFormat;

public class UnicodeDemo {

  public static void main(String[] args) {
    printLettersWithAccents();
    printNamesWithAccents();
  }

  private static void printNamesWithAccents() {
    char eacute = Unicode.charOf('e', Unicode.Accent.ACUTE);
    char Icirc = Unicode.charOf('I', Unicode.Accent.CIRCUMFLEX);
    char ccedil = Unicode.charOf('c', Unicode.Accent.CEDILLA);
    char ntilde = Unicode.charOf('n', Unicode.Accent.TILDE);

    System.out.println(MessageFormat.format("Montr{0}al", eacute));
    System.out.println(MessageFormat.format("Qu{0}bec", eacute));
    System.out.println(MessageFormat.format("{0}le Saint-Fran{1}ois", Icirc, ccedil));
    System.out.println(MessageFormat.format("Ca{0}on", ntilde));
  }

  private static void printLettersWithAccents() {
    for (char vowel : Unicode.getLettersWithAccent()) {
      for (Unicode.Accent accent : Unicode.Accent.values()) {
        int unicode = Unicode.codeOf(vowel, accent);
        char ch = (char) unicode;

        String msg = MessageFormat.format("{0} {1} : {2} ({3})", vowel, accent, unicode, ch);
        System.out.println(msg);
      }

      System.out.println();
    }

    System.out.println();
  }

}
