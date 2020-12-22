package com.marcosavard.commons.ling.fr;

import static com.marcosavard.commons.ling.fr.Gender.FEMININE;
import static com.marcosavard.commons.ling.fr.Gender.MASCULINE;
import static com.marcosavard.commons.ling.fr.Gender.UNKNOWN;
import java.util.Arrays;
import java.util.List;

public class GenderFinder {

  public Gender findGender(String word) {

    WordGender wordGender = new WordGender(word);

    boolean hasPrefixVerb = findForPrefixVerb(wordGender);

    if (!hasPrefixVerb) {
      int beginning = Math.max(0, word.length() - 2);
      String suffix = word.substring(beginning);

      boolean suffixE = suffix.equals("ae");
      suffixE |= suffix.equals("be");
      suffixE |= suffix.equals("ce");
      // suffixE |= suffix.equals("de");

      if (suffixE) {
        findSuffixE(wordGender);
      }
    }

    return wordGender.gender;
  }

  private void findSuffixE(WordGender wordGender) {
    wordGender.apply("ae", MASCULINE, "");
    wordGender.apply("ee", MASCULINE, "");
    wordGender.apply("oe", FEMININE, "");

    wordGender.apply("rabe", UNKNOWN, "");
    wordGender.apply("abe", MASCULINE, "souabe,syllabe,trabe");
    wordGender.apply("bbe", MASCULINE, "");
    wordGender.apply("èbe", MASCULINE, "glèbe,plèbe");
    wordGender.apply("ibe", FEMININE, "caribe,scribe");
    wordGender.apply("lbe", MASCULINE, "");
    wordGender.apply("ambe", MASCULINE, "flambe,gambe,jambe");
    wordGender.apply("ombe", FEMININE, "rhombe,strombe");
    wordGender.apply("mbe", FEMININE, "corymbe,djembe,limbe,nimbe");
    wordGender.apply("phobe", UNKNOWN, "");
    wordGender.apply("robe", FEMININE, "microbe,orobe");
    wordGender.apply("obe", MASCULINE, "");
    wordGender.apply("orbe", MASCULINE, "euphorbe,sanguisorbe,sorbe");
    wordGender.apply("verbe", MASCULINE, "");
    wordGender.apply("irbe", MASCULINE, "");
    wordGender.apply("ube", MASCULINE, "aube,cachoube,caroube,daube,kachoube");
    wordGender.apply("ybe", MASCULINE, "");

    wordGender.apply("space", MASCULINE, "espace");
    wordGender.apply("ace", FEMININE,
        "ace,androsace,biface,biplace,lovelace,palace,pancrace,surplace");
    wordGender.apply("ance", FEMININE, "inespérance");
    wordGender.apply("thèce", MASCULINE, "");
    wordGender.apply("cice", MASCULINE, "");
    wordGender.apply("dice", MASCULINE, "blandice,immondice");
    wordGender.apply("ence", FEMININE, "silence");
    wordGender.apply("fice", MASCULINE, "office");
    wordGender.apply("ince", FEMININE, "prince");
    wordGender.apply("lice", FEMININE, "calice,cilice,délice,slice,supplice");
    wordGender.apply("pice", MASCULINE, "épice");
    wordGender.apply("mice", MASCULINE, "");
    wordGender.apply("rice", FEMININE, "caprice,dentifrice,patrice");
    wordGender.apply("tice", FEMININE, "armistice,interstice,solstice");
    wordGender.apply("novice", UNKNOWN, "");
    wordGender.apply("vice", MASCULINE, "");
    wordGender.apply("nonce", MASCULINE, "");
    wordGender.apply("once", FEMININE, "oponce,quinconce");
    wordGender.apply("oce", MASCULINE, "féroce,noce,précoce");

    wordGender.apply("merce", MASCULINE, "");
    wordGender.apply("terce", MASCULINE, "");
    wordGender.apply("rce", FEMININE, "chaource,divorce");
    wordGender.apply("uce", FEMININE, "capuce,duce,pouce,prépuce");

    wordGender.apply("e", FEMININE, "");
  }

  private boolean findForPrefixVerb(WordGender wordGender) {
    boolean startWithVerb = false;
    String word = wordGender.word;

    startWithVerb |= word.startsWith("abat-");
    startWithVerb |= word.startsWith("aide-");
    startWithVerb |= word.startsWith("appuie-");
    startWithVerb |= word.startsWith("bourre-");
    startWithVerb |= word.startsWith("brise-");

    startWithVerb |= word.startsWith("cache-");
    startWithVerb |= word.startsWith("capte-");
    startWithVerb |= word.startsWith("casse-");
    startWithVerb |= word.startsWith("chasse-");
    startWithVerb |= word.startsWith("coupe-");
    startWithVerb |= word.startsWith("cure-");

    startWithVerb |= word.startsWith("emporte-");
    startWithVerb |= word.startsWith("entre-");
    startWithVerb |= word.startsWith("essuie-");
    startWithVerb |= word.startsWith("fume-");
    startWithVerb |= word.startsWith("garde-");
    startWithVerb |= word.startsWith("gâte-");
    startWithVerb |= word.startsWith("gobe-");
    startWithVerb |= word.startsWith("hors-");

    startWithVerb |= word.startsWith("lâcher-");
    startWithVerb |= word.startsWith("lance-");
    startWithVerb |= word.startsWith("lave-");
    startWithVerb |= word.startsWith("lève-");
    startWithVerb |= word.startsWith("monte-");
    startWithVerb |= word.startsWith("ouvre-");
    startWithVerb |= word.startsWith("pare-");
    startWithVerb |= word.startsWith("pince-");
    startWithVerb |= word.startsWith("porte-");

    startWithVerb |= word.startsWith("ramasse-");
    startWithVerb |= word.startsWith("remonte-");
    startWithVerb |= word.startsWith("remue-");
    startWithVerb |= word.startsWith("sèche-");
    startWithVerb |= word.startsWith("taille-");
    startWithVerb |= word.startsWith("tire-");

    return startWithVerb;
  }

  private boolean startWithVerb(String word) {
    boolean startWithVerb = false;
    startWithVerb |= word.startsWith("abat-");
    startWithVerb |= word.startsWith("aide-");
    startWithVerb |= word.startsWith("appuie-");
    startWithVerb |= word.startsWith("bourre-");

    startWithVerb |= word.startsWith("cache-");
    startWithVerb |= word.startsWith("capte-");
    startWithVerb |= word.startsWith("casse-");
    startWithVerb |= word.startsWith("chasse-");
    startWithVerb |= word.startsWith("coupe-");
    startWithVerb |= word.startsWith("cure-");

    startWithVerb |= word.startsWith("emporte-");
    startWithVerb |= word.startsWith("entre-");
    startWithVerb |= word.startsWith("essuie-");
    startWithVerb |= word.startsWith("fume-");
    startWithVerb |= word.startsWith("garde-");
    startWithVerb |= word.startsWith("gobe-");
    startWithVerb |= word.startsWith("hors-");

    startWithVerb |= word.startsWith("lâcher-");
    startWithVerb |= word.startsWith("lance-");
    startWithVerb |= word.startsWith("lave-");
    startWithVerb |= word.startsWith("ouvre-");
    startWithVerb |= word.startsWith("pare-");
    startWithVerb |= word.startsWith("pince-");
    startWithVerb |= word.startsWith("porte-");

    startWithVerb |= word.startsWith("ramasse-");
    startWithVerb |= word.startsWith("remonte-");
    startWithVerb |= word.startsWith("remue-");
    startWithVerb |= word.startsWith("sèche-");
    startWithVerb |= word.startsWith("taille-");
    startWithVerb |= word.startsWith("tire-");

    return startWithVerb;
  }


  private boolean findSuffixE(String word) {
    Boolean gender = null;

    // findSuffix();

    gender = findIfSuffix(word, gender, "ae", true, ""); // reggae
    gender = findIfSuffix(word, gender, "ee", true, ""); // frisbee
    gender = findIfSuffix(word, gender, "oe", true, "");

    gender = findIfSuffix(word, gender, "abe", true, "souabe,syllabe,trabe");
    gender = findIfSuffix(word, gender, "bbe", true, "");
    gender = findIfSuffix(word, gender, "èbe", true, "glèbe,plèbe");
    gender = findIfSuffix(word, gender, "ibe", false, "caribe,scribe");
    gender = findIfSuffix(word, gender, "lbe", true, "");
    gender = findIfSuffix(word, gender, "ambe", true, "flambe,gambe,jambe");
    gender = findIfSuffix(word, gender, "ombe", false, "rhombe,strombe");
    gender = findIfSuffix(word, gender, "mbe", true, "corymbe,djembe,limbe,nimbe");
    gender = findIfSuffix(word, gender, "obe", true, "");
    gender = findIfSuffix(word, gender, "ube", true, "aube,cachoube,caroube,daube,kachoube");
    gender = findIfSuffix(word, gender, "ybe", true, "");



    gender = findIfSuffix(word, gender, "sme", true, "");

    gender = (gender == null) ? false : gender;

    return gender;
  }

  private Boolean findIfSuffix(String word, Boolean gender, String suffix, boolean expected,
      String exceptions) {
    if (gender == null) {
      if (word.endsWith(suffix)) {
        List<String> exceptionList = Arrays.asList(exceptions.split(","));
        boolean exception = exceptionList.contains(word);
        gender = exception ? !expected : expected;
      }
    }

    return gender;
  }

  private static final class WordGender {
    private String word;
    private Gender gender;

    public WordGender(String word) {
      this.word = word;
    }

    public void apply(String suffix, Gender gender, String exceptions) {
      Gender opposite = null;
      if (gender == Gender.FEMININE) {
        opposite = Gender.MASCULINE;
      } else if (gender == Gender.MASCULINE) {
        opposite = Gender.FEMININE;
      }

      if (this.gender == null) {
        if (word.endsWith(suffix)) {
          List<String> exceptionList = Arrays.asList(exceptions.split(","));
          boolean exception = exceptionList.contains(word);
          this.gender = exception ? opposite : gender;
        }
      }
    }

    public void find(String string, Gender masculine, String string2) {
      // TODO Auto-generated method stub

    }

  }

}
