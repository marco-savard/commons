package com.marcosavard.commons.pseudo;

import java.io.PrintStream;

public class PseudoLanguageDemo {
  private static final String HOMER = "Alors survint l'âme du Thébain Tirésias, " + //
      "le sceptre d'or en main. Il me reconnut et me dit: «Descendant de Zeus, " + //
      "fils de Laërte, Ulysse aux mille expédients, » pourquoi donc, malheureux, " + //
      "quittant la lumière du soleil, es-tu venu voir les morts et la région sans joie? " + //
      "Mais éloigne-toi de la fosse, écarte la pointe de ton épée, que je boive du sang " + //
      "et te dise la vérité. »";

  private static final String[] SENTENCES = new String[] { //
      "Est-ce rouge?", //
      "Non", //
      "Est-ce petit?", //
      "Non", //
      "Est-ce grand?", //
      "Oui", //
      "Est-ce un grand poisson?", //
      "Oui", //
      "Est-ce un requin?", //
      "Oui! Tu as trouvé!"};

  public static void main(String[] args) {
    PseudoLanguage pseudoLatin = PseudoLanguage.create("Pseudo Latin") //
        .withLetters("eituasrnomcpldbgvfhx") //
        .withPatterns("CV", "CVC", "VC") //
        .withDiphtongs("qu", "ae", "ll") //
        .withTerminaisons("us", "um", "a", "ae", "am") //
        .withCommonWords("id", "ex", "ad", "de", "ab", "et") //
        .withCommonWords("in", "ut", "si", "tu") //
        .withCommonWords("sed", "cum", "pro", "non", "per", "qui") //
        .withCommonWords("hoc", "nam", "aut", "hic", "nil", "dum") //
        .withCommonWords("idem", "ipse", "sine", "nunc", "ante", "post");

    PseudoLanguage pseudoElfic = PseudoLanguage.create("Pseudo Elfic") //
        .withLetters("ekwnsriatdhulgombf") //
        .withPatterns("CVC", "CCVC") //
        .withDiphtongs("kn", "gw", "kw", "dh", "gh", "kh", "rh", "sh", "th") //
        .withTerminaisons("alf", "org", "ek", "ark", "ok") //
        .withCommonWords("kert", "elk", "alf", "tenn", "kam", "bar") //
        .withCommonWords("hil", "din", "gwe", "kno", "we", "wen");

    PseudoLanguage pseudoSlavic = PseudoLanguage.create("Pseudo Slavic") //
        .withLetters("erntaidslogjkmfvbup") //
        .withPatterns("CVC", "CCVC") //
        .withDiphtongs("ov", "ie", "ia", "sk", "kv", "ts", "vl", "st") //
        .withDiphtongs("sti", "vr", "dr", "ski", "zn", "dja", "tch", "dj") //
        .withTerminaisons("ov", "ski", "ova", "skia", "tcha") //
        .withCommonWords("od", "tche", "sto", "da", "njie", "ov") //
        .withCommonWords("dobro", "smo", "tchu", "ovdje", "znam", "kad");

    PseudoLanguage pseudoSemitic = PseudoLanguage.create("Pseudo Semitic") //
        .withLetters("aliurbmhws") //
        .withPatterns("CVCVC", "CVCVCV") //
        .withDiphtongs("al", "ba", "mar", "sab", "said", "lah", "mah", "sah", "bah") //
        .withTerminaisons("ba", "id", "dah", "mah", "la") //
        .withCommonWords("suk", "al", "af", "wan", "ana", "sif") //
        .withCommonWords("na", "af", "muh", "lai", "sa", "za");

    PseudoLanguage pseudoNippon = PseudoLanguage.create("Pseudo Nippon") //
        .withLetters("aoieutknwgjy") //
        .withPatterns("CV", "CVCV", "CVC", "CVCVC") //
        .withDiphtongs("dai", "de", "ji", "ky", "ne", "no") //
        .withDiphtongs("sh", "ta", "ts", "ya", "yo", "zu") //
        .withTerminaisons("ka", "kan", "ko", "ki", "ta", "tan", "to", "ti") //
        .withTerminaisons("na", "nan", "no", "ni", "wa", "wan", "wo", "wi") //
        .withCommonWords("ka", "ko", "ki", "ta", "to", "ti") //
        .withCommonWords("na", "no", "ni", "wa", "wo", "wi");

    TextPrinter printer = new TextPrinter(System.out, 80);
    printer.print("[Original]");
    printer.print(HOMER);
    printer.print();

    PseudoLanguage[] languages = new PseudoLanguage[] { //
        pseudoLatin, pseudoElfic, pseudoSlavic, pseudoSemitic, pseudoNippon};

    for (PseudoLanguage language : languages) {
      printer.print("[" + language.getLanguageName() + "]");
      String translated = language.translate(HOMER);
      printer.print(translated);
      printer.print();
    }

    // translateSentence(pseudoLatin);
    // translateSentences(pseudoLatin);
  }

  private static void translateText(PseudoLanguage pseudoLanguage, String original) {
    TextPrinter printer = new TextPrinter(System.out, 80);
    printer.print(original);
    printer.print();

    String translation = pseudoLanguage.translate(original);
    printer.print(translation);
  }

  private static void translateSentence(PseudoLanguage pseudoLanguage) {
    String original = "Lorsque Auguste etait empereur de toutes les nations du monde";
    String translation = pseudoLanguage.translate(original);
    System.out.println(original + " -> " + translation);
    System.out.println();
  }

  private static void translateSentences(PseudoLanguage language) {
    for (String sentence : SENTENCES) {
      String translation = language.translate(sentence);
      System.out.println(sentence);
      System.out.println("  " + translation);
      System.out.println();
    }
  }

  private static class TextPrinter {
    private PrintStream output;
    private int width;
    private int currentPosition = 0;

    public TextPrinter(PrintStream output, int width) {
      this.output = output;
      this.width = width;
    }

    public void print() {
      output.println();
      currentPosition = 0;
    }

    public void print(String text) {
      String[] words = text.split("\\s+");

      for (String word : words) {
        boolean fitInLine = (currentPosition + word.length() <= width);

        if (fitInLine) {
          output.print(word);
          output.print(" ");
          currentPosition += word.length() + 1;
        } else {
          output.println();
          output.print(word);
          output.print(" ");
          currentPosition = word.length() + 1;
        }
      }

      output.println();
    }
  }

}
