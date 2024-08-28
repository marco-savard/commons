package com.marcosavard.commons.quiz.general;

import java.util.ArrayList;
import java.util.List;

public class CategoryName {

  public static final List<String> CATEGORIES =
      List.of( //
          ";pronom personnel sujet;je;tu;il;elle;on;nous;vous;ils;elles", //
          ";pronom personnel objet;me;te;se;nous;vous", //
          ";adjectif démonstratif;ce;cet;cette", //
          ";article défini;la;le;les", //
          ";article indéfini;un;une;des", //
          ";particule;de;du;des", //
          ";a moi;mon;ma;mes", //
          ";a toi;ton;ta;tes", //
          ";a lui;son;sa;ses", //
          ";a nous;notre;nos", //
          ";a vous;votre;vos", //
          ";conjonction;car;donc;et;mais;ni;or;ou", //
          "NATURE;conifère;if;pin;sapin;cèdre;mélèze;cyprès;séquoia;genévrier", //
          "EUROPE;fleuve d'Italie;Po;Tibre", //
          "EUROPE;fleuve de France;Meuse;Seine;Loire;Rhone", //
          "EUROPE;fleuve d'Allemagne;Ruhr;Oder;Rhin;Elbe", //
          "EUROPE;fleuve de Russie;Ob;Don;Amour", //
          "EUROPE;lac de France;Annecy", //
          "EUROPE;lac d'Italie;Côme", //
          "EUROPE;lac de Suisse;Léman;Zurich", //
          ";fleuve d'Egypte;Nil", //
          ";fleuve des Etats-Unis;Mississipi;Columbia;Hudson",
          ";lac d'Amérique du Nord;Erié;Huron;Ontario;Champlain",
          ";lac des Etats-Unis;Michigan;Tahoe;Mead;Powell",
          ";lac du Canada;Winnipeg", //
          ";jeu;go;domino;échecs;dames;poker;backgammon", //
          ";note;do;re;mi;fa;sol;la;si" //
          );

  public static List<String[]> getCategories() {
    List<String[]> categories = new ArrayList<>();

    for (int i = 0; i < CATEGORIES.size(); i++) {
      List<String> words = List.of(CATEGORIES.get(i).split(";"));

      for (int j = 2; j < words.size(); j++) {
        categories.add(new String[] {words.get(1), words.get(j)});
      }
    }

    return categories;
  }
}
