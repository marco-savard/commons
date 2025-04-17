package com.marcosavard.commons.quiz.general;

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

  public static List<String> getAbbreviations() {
    return ABBREVIATIONS;
  }

  private static final List<String> ADVERBS =
      List.of( //
          // adverbs
          "ça;cela", //
          "ci;ici", //
          "en;dans;dedans", //
          "mi;demi;moitié", //
          "complètement;entièrement;totalement;absolument;intégralement", //
          "discrètement;subtilement;furtivement", //
          "rapidement;vite;promptement;prestement;hâtivement;expéditivement", //
          "lentement;modérément;tranquillement", //
          "sûrement;assurément;certainement;inconstestablement;indubitablememt", //
          "souvent;fréquemment;couramment;régulièrement", //
          "soudainement;brusquement;subitement;inopinément" //
          );

  public static final List<String> NON_COUNTABLE_NOUNS =
      List.of( //
          "argent;fric;pognon;monnaie;pécule", //
          "faim;fringale;creux;appétit", //
          "temps;durée;période;moment", //
          "nourriture;aliment;vivres;denrée", //
          "viande;chair;protéine", //
          "amour;affection", //
          "bonheur;félicité", //
          "intelligence;esprit;sagacité", //
          "courage;bravoure;vaillance;témérité", //
          "liberté;autonomie;émancipation", //
          "justice;équité;droiture", //
          "tristesse;chagrin;peine;mélancolie");

  public static final List<String> COUNTABLE_NOUNS =
      List.of( //
          // nouns
          "singe;primate", //
          "oiseau;volatile", //
          "cochon;porc", //
          "panthère;léopard", //
          "puma;couguar", //
          "renard;goupil", //
          "animal;bête", //
          "cheval;monture", //
          "aï;paresseux", //
          "os;squelette", //
          "homme;mec;type;individu", //
          "enfant;gamin;jeune;bambin;marmot", //
          "ami;copain;camarade;compagnon", //
          "travail;emploi;occupation;boulot;métier;profession;tâche", //
          "illustrateur;dessinateur", //
          "infirmier;soignant", //
          "médecin;docteur", //
          "enseignant;professeur", //
          "avocat;juriste", //
          "écrivain;auteur", //
          "maçon;bâtisseur", //
          "vendeur;commerçant", //
          "pharmacien;apothicaire", //
          "charpentier;menuisier", //
          "jardinier;horticulteur", //
          "chauffeur;conducteur", //
          "acteur;comédien", //
          "marin;navigateur", //
          "banquier;financier", //
          "boulanger;patissier", //
          "couturier;styliste", //
          "opticien;lunetier", //
          "coiffeur;barbier", //
          "esthéticienne;cosméticienne", //
          "agriculteur;fermier", //
          "antiquaire;brocanteur", //
          "cartographe;géomaticien", //
          "chasseur;trappeur", //
          "journaliste;reporter", //
          "linguiste;philologue", //
          "magistrat;juge", //
          "médiateur;conciliateur", //
          "musicien;instrumentiste", //
          "orthophoniste;logopède", //
          "pilote;aviateur", //
          "viticulteur;vigneron", //
          "tailleur;couturier", //
          "topographe;geomètre", //
          "maison;habitation;domicile;résidence;foyer;logis;demeure", //
          "lit,couchette,matelas", //
          "voiture;automobile;bagnole;véhicule", //
          "livre;ouvrage;bouquin;volume;manuel;recueil", //
          "vêtement;habit;tenue;costume", //
          "mets;plat;repas;festin", //
          "collation;gouter;encas;bouchée", //
          "délice;friandise;gourmandise", //
          "coutume;us", //
          "boisson;liquide;breuvage;potion", //
          "bateau;navire;vaisseau;embarcation", //
          "endroit;site;lieu;zone", //
          "rue;voie;route;avenue;boulevard;chemin", //
          "village;hameau;commune", //
          "ville;agglomération;métropole", //
          "région;territoire;contrée", //
          "peuple;nation;population", //
          "tribu;clan;peuplade", //
          "nation;patrie;état;pays", //
          "planète;astre");

  public static final List<String> ADJECTIVES =
      List.of( //
          // adjectives
          "beau;joli;magnifique;ravissant;splendide;élégant;somptueux", //
          "grand;immense;énorme;géant;gigantesque;vaste;majestueux;imposant;colossal", //
          "petit;minuscule;réduit;nain;compacte;infime", //
          "triste;morose;déprimé;attristé;chagriné;mélancolique", //
          "intelligent;malin;futé;astucieux;ingénieux;brillant", //
          "rapide;vif;célère;prompt;véloce;preste;expéditif", //
          "fort;puissant;robuste;costaud", //
          "agréable;confortable", //
          "aimable;sympatique", //
          "amusant;comique;drôle;hilarant", //
          "ancien;ex", //
          "aperçu;vu", //
          "ardent;enthousiaste;passionné", //
          "ardu;difficile;pénible", //
          "avantageux;bénéfique;favorable", //
          "aventureux;téméraire", //
          "brillant;éclatant", //
          "bu;ingéré", //
          "calme;paisible;serein", //
          "capable;compétent", //
          "captivant;entrainant;fascinant", //
          "célèbre;connu;notoire", //
          "céleste;divin", //
          "charmant;séduisant", //
          "classique;traditionel", //
          "complet;entier;intègre", //
          "constant;regulier", //
          "correct;juste", //
          "connu;su", //
          "créatif;inventif", //
          "dangereux;risqué", //
          "déchiffré;lu", //
          "délicieux;savoureux", //
          "dévêtu;nu", //
          "déplacé;mu", //
          "du;fallu", //
          "élégant;raffiné", //
          "énigmatique;mystérieux", //
          "équitable;juste", //
          "équilibre;harmonieux", //
          "érudit;savant", //
          "essentiel;fondamental;important", //
          "eu;obtenu", //
          "évident;manifeste", //
          "exact;précis", //
          "excitant;stimulant", //
          "facile;simple", //
          "flexible;malléable", //
          "fructueux;productif", //
          "humble;modeste", //
          "indispensable;nécessaire;vital", //
          "jovial;joyeux", //
          "notable;remarquable", //
          "adolescent;jeune", //
          "américain;yankee", //
          "espagnol;ibère", //
          "portugais;lusitanien", //
          "helvète;suisse", //
          "allemand;germain;teuton;prussien", //
          "finlandais;finnois", //
          "grec;hellène;byzantin", //
          "turc;ottoman", //
          "juif;hébreu", //
          "asiatique;oriental",
          "malais;malaisien", //
          "japonais;nippon");

  public static final List<String> ABBREVIATIONS =
          List.of(   "am;amplitude maximale",
                  "aq;assurance qualité", //
                  "bd;bande dessinée", //
                  "ca;conseil d`administration",
                  "cc;copie conforme",
                  "cp;code postal",
                  "cq;contrôle qualité", //
                  "cs;centre de service",
                  "fm;fréquence modulée",
                  "et;extra-terrestre",
                  "hd;haute définition",
                  "ia;intelligence artificielle",
                  "ir;infra rouge",
                  "iu;interface utilisateur",
                  "lm;long métrage",
                  "pv;procès-verbal",
                  "rh;ressource humaine",
                  "si;système international",
                  "ti;technologies de l`information",
                  "tv;télévision",
                  "ua;unité astronomique",
                  "uv;ultra violet",
                  "vo;version originale"
          );

  public static final List<String> ABBREVIATIONS_LATINES =
      List.of( //
          "am;ante meridiem",
          "cv;curriculum vitae",
          "cf;confer",
          "eg;exempli gratia",
          "etc;et cetera", //
          "ie;id est",
          "lb;livre",
          "nb;nota bene",
          "pm;post meridiem",
          "ps;post scriptum", //
          "qed;quod erat demonstrandum",
          "rip;requiescat in pace",
          "vs;versus");

  public static final List<String> ABBREVIATIONS_EUROPE =
      List.of( //
          "bce;banque centrale européenne",
          "ue;union européenne",
          "rfa;république fédérale allemande",
          "rda;république démocratique allemande");

  public static final List<String> ABBREVIATIONS_AMERIQUE =
      List.of( //
          "usa;united states of America");

  //
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
    } else if (word.endsWith("el")) {
      feminine = word.substring(0, len - 1) + "lle";
    } else if (word.endsWith("en")) {
      feminine = word.substring(0, len - 1) + "nne";
    } else if (word.endsWith("ex")) {
      feminine = word;
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
    } else if (word.equals("eu")) {
      plural = "eus";
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
