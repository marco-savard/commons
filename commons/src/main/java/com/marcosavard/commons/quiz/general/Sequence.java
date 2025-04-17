package com.marcosavard.commons.quiz.general;

import java.util.ArrayList;
import java.util.List;

public class Sequence {

  private static final List<String> SEQUENCES =
      List.of( //
          // sequence
          "TIME;avant;après;le printemps;l'été;l'automne;l'hiver;le printemps", //
          "TIME;avant;après;l'aube;le matin;midi;l'après-midi;le crépuscule;le soir;la nuit", //
          "TIME;moins long que;plus long que;la seconde;la minute;l'heure;le jour;la semaine;le mois;l'an;la décennie;le siècle;le millénaire", //
          "TIME;avant;après;le précambrien;le cambrien;l'ordovicien;le silurien;le dévonien;le carbonifère;le permien;le trias;le jurassique;le crétacé;le paléocène;l'éocène;l'ologocène;le miocène;le pliocène;le pléistocène;l'holocène", //
          "TIME;avant;après;la préhistoire;l'antiquité;le moyen-âge;la renaissance;l'époque classique;l'époque moderne", //
          "NATURE;avant;après;la germination;la croissance;la floraison;la pollinisation;la fructification;la maturation", //
          "NATURE;plus petit que;plus grand que;le ru;le ruisselet;le ruisseau;le torrent;la riviève;le fleuve;l'estuaire", //
          "NATURE;plus petit que;plus grand que;la flaque;la mare;l'étang;le lac;la mer;l'océan", //
          "NATURE;moins haut que;plus haut que;la butte;la colline;le mont;le pic;le sommet", //
          "NATURE;moins puissant que;plus puissant que;la brise;le vent;la rafale;la tempête;le cyclone", //
          "NATURE;climat plus froid que;climat plus chaud que;polaire;arctique;subarctique;tempéré;subtropical;tropical;équatorial", //
          "NATURE;plus bas que;plus haut que;la troposphère;la stratosphère;la mésosphère;la thermosphère;l'exosphère", //
          "ANTIQUE;dirigeait Rome avant;dirigeait Rome après;César;Auguste;Tibère;Caligula;Claude;Néron", //
          "ANTIQUE;moins gradé que;plus gradé que;le légionnaire;le chevalier;le décurion;l'optio;le centurion;le commandant;l'empereur", //
          "ANTIQUE;moins titré que;plus titré que;l'esclave;l'affranchi;le prolétaire;le plébéien;le patricien;le sénateur;le tribun;le censeur;l'édile;le questeur;le préteur;le consul;l'empereur", //
          "MEDIEVAL;moins titré que;plus titré que;le chevalier;le seigneur;le baron;le vicomte;le comte;le marquis;le duc;l'archiduc;le prince;le roi;l'empereur", //
          "MEDIEVAL;roi avant;roi après;Clovis;Clodomir;Thierry;Clotaire;Dagobert;Pépin;Charlemagne", //
          "SOCIETY;plus jeune que;plus vieux que;le nourisson;le bébé;le bambin;l'enfant;l'adolescent;le trentenaire;le quadragénaire;le quinquagénaire;le sexagénaire;le septuagénaire;l'octogénaire;le vieillard", //
          "SOCIETY;moins instruit que;plus instruit que;l'écolier;l'élève;l'étudiant;le bachelier;le maitre;le doctorant;le docteur;le professeur;le doyen;le recteur", //
          "SOCIETY;moins gradé que;plus gradé que;le soldat;le caporal;le sergent;l'adjudant;le lieutenant;le capitaine;le commandant;le colonel;le général;le maréchal", //
          "SOCIETY;moins titré que;plus titré que;le diacre;le prêtre;l'évêque;l'archevêque;le cardinal;le pape", //
          "SOCIETY;plus petit que;plus grand que;le hameau;le village;le bourg;la ville;la métropole;la mégapole;la mégalopole", //
          "SOCIETY;plus petit que;plus grand que;le canot;la pirogue;la barque;le zodiac;le yacht;la navette;le chalutier;le navire;le paquebot", //
          "SOCIETY;vaut moins que;vaut plus que;le bronze;l'argent;l'or;le platine",
          "GAME;vaut moins que;vaut plus que;le valet;la dame;le roi;l'as;le joker",
          "ART;moins volumineux que;plus volumineux que;le feuillet;la brochure;le livret;le livre;le dictionnaire;l'encyclopédie", //
          "ART;plus bas que;plus haut que;do;ré;mi;fa;sol;la;si");

  //

  public static List<String[]> getSequencePairs() {
    List<String[]> pairs = new ArrayList<>();

    for (String sequence : SEQUENCES) {
      String[] parts = sequence.split(";");

      for (int i = 3; i < parts.length; i++) {
        String previous = (i == 3) ? null : parts[i - 1];
        String next = (i < parts.length - 1) ? parts[i + 1] : null;
        String before = (next == null) ? null : join(parts[1], next);
        String after = (previous == null) ? null : join(parts[2], previous);
        addIf(pairs, new String[] {before, strip(parts[i])}, (before != null));
        addIf(pairs, new String[] {after, strip(parts[i])}, (after != null));
      }
    }

    return pairs;
  }

  private static String strip(String original) {
    String stripped = original.replace('\'', ' ');
    stripped = stripped.replace("le ", "");
    stripped = stripped.replace("la ", "");
    stripped = stripped.replace("l ", "");
    stripped = stripped.strip();
    return stripped;
  }

  private static String join(String before, String next) {
    String joined = before + " " + next;
    return joined;
  }

  private static void addIf(List<String[]> list, String[] item, boolean condition) {
    if (condition) {
      list.add(item);
    }
  }

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
    } else if (word.endsWith("eu")) {
      plural = word + "x";
    } else if (word.endsWith("s") || word.endsWith("x") | word.endsWith("z")) {
      plural = word;
    } else {
      plural = word + "s";
    }

    return plural;
  }
}
