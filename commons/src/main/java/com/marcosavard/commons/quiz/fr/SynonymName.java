package com.marcosavard.commons.quiz.fr;

import java.util.ArrayList;
import java.util.List;

public class SynonymName {
  public static List<String> getAdverbs() {
    return ADVERBS;
  }

  public static List<String> getNonCountableNouns() {
    return NON_COUNTABLE_NOUNS;
  }

  public static List<String> getCountableNouns() {
    return COUNTABLE_NOUNS;
  }

  public static List<String> getAdjectives() {
    return ADJECTIVES;
  }

  private static final List<String> ADVERBS =
      List.of( //
          // adverbs
          "rapidement;vite;promptement;prestement;hâtivement;expéditivement", //
          "lentement;modérément;tranquillement", //
          "sûrement;assurément;certainement;inconstestablement;indubitablememt", //
          "souvent;fréquemment;couramment;régulièrement", //
          "complètement;entièrement;totalement;absolument;intégralement", //
          "soudainement;brusquement;subitement;inopinément", //
          "discrètement;subtilement;furtivement");

  public static final List<String> NON_COUNTABLE_NOUNS =
      List.of( //
          "argent;fric;pognon;monnaie;pécule", //
          "faim;fringale;creux;appétit", //
          "temps;durée;période;moment", //
          "nourriture;aliment;vivres;denrée", //
          "viande;chair;protéine" //
          );

  public static final List<String> COUNTABLE_NOUNS =
      List.of( //
          // nouns
          "maison;habitation;domicile;résidence;foyer;logis;demeure", //
          "enfant;gamin;jeune;bambin;marmot", //
          "voiture;automobile;bagnole;véhicule", //
          "travail;emploi;occupation;boulot;métier;profession;tâche", //
          "ami;copain;camarade;compagnon", //
          "livre;ouvrage;bouquin;volume;manuel;recueil", //
          "singe;primate", //
          "oiseau;volatile", //
          "mets;plat;repas;festin", //
          "collation;gouter;encas;bouchée", //
          "délice;friandise;gourmandise", //
          "boisson;liquide;breuvage;potion", //
          "endroit;site;lieu;zone", //
          "tribu;clan;peuplade", //
          "région;territoire;contrée", //
          "rue;voie;route;avenue;boulevard;chemin", //
          "village;hameau;commune", //
          "peuple;nation;population", //
          "nation;patrie;état;pays" //
          );

  public static final List<String> ADJECTIVES =
      List.of( //
          // adjectives
          "beau;joli;magnifique;ravissant;splendide;élégant;somptueux", //
          "grand;immense;énorme;gigantesque;vaste;majestueux;imposant;colossal", //
          "petit;minuscule;réduit;nain;compacte;infime", //
          "triste;morose;déprimé;attristé;chagriné;mélancolique", //
          "intelligent;malin;futé;astucieux;ingénieux;brillant", //
          "rapide;vif;célère;prompt;véloce;preste;expéditif", //
          "fort;puissant;robuste;costaud", //
          "américain;yankee", //
          "espagnol;ibère", //
          "helvète;suisse", //
          "allemand;germain;teuton", //
          "grec;hellène;byzantin", //
          "turc;ottoman", //
          "juif;hébreu", //
          "asiatique;oriental",
          "japonais;nippon");

  public static List<String[]> getPairs(List<String> synonyms) {
    List<String[]> pairs = new ArrayList<>();

    for (int i = 0; i < synonyms.size(); i++) {
      for (int j = i + 1; j < synonyms.size(); j++) {
        pairs.add(new String[] {synonyms.get(i), synonyms.get(j)});
        pairs.add(new String[] {synonyms.get(j), synonyms.get(i)});
      }
    }

    return pairs;
  }

  public static List<String> toFeminines(List<String> words) {
    List<String> feminines = new ArrayList<>();

    for (String synonyms : words) {
      feminines.add(toFeminines(synonyms));
    }

    return feminines;
  }

  public static String toFeminines(String synonyms) {
    String[] words = synonyms.split(";");
    List<String> feminines = new ArrayList<>();

    for (String word : words) {
      feminines.add(toFeminine(word));
    }

    return String.join(";", feminines);
  }

  public static String toFeminine(String word) {
    int len = word.length();
    String feminine;

    if (word.endsWith("grec")) {
      feminine = word + "que";
    } else if (word.endsWith("eau")) {
      feminine = word.substring(0, len - 2) + "lle";
    } else if (word.endsWith("eux")) {
      feminine = word.substring(0, len - 1) + "se";
    } else if (word.endsWith("c")) {
      feminine = word.substring(0, len - 1) + "que";
    } else if (word.endsWith("f")) {
      feminine = word.substring(0, len - 1) + "ve";
    } else if (word.endsWith("e")) {
      feminine = word;
    } else {
      feminine = word + "e";
    }

    return feminine;
  }

  public static List<String> toPlurals(List<String> words) {
    List<String> plurals = new ArrayList<>();

    for (String synonyms : words) {
      plurals.add(toPlurals(synonyms));
    }

    return plurals;
  }

  public static String toPlurals(String synonyms) {
    String[] words = synonyms.split(";");
    List<String> plurals = new ArrayList<>();

    for (String word : words) {
      plurals.add(toPlural(word));
    }

    return String.join(";", plurals);
  }

  public static String toPlural(String word) {
    int len = word.length();
    String plural;

    if (word.endsWith("ail")) {
      plural = word.substring(0, len - 2) + "ux";
    } else if (word.endsWith("al")) {
      plural = word.substring(0, len - 1) + "ux";
    } else if (word.endsWith("ou")) {
      plural = word + "s";
    } else if (word.endsWith("au")) {
      plural = word + "x";
    } else if (word.endsWith("s") || word.endsWith("x") | word.endsWith("z")) {
      plural = word;
    } else {
      plural = word + "s";
    }

    return plural;
  }
}
