package com.marcosavard.library.opencsv.dgeq.res;

public class ElecteurInscrit {
  private String nom_circonscription;
  private int code_circonscription;
  private int nb_electeurs_decret_av_rev;
  private int nb_electeurs_ap_rev_spe;

  public int getCodeCirconscription() {
    return code_circonscription;
  }

  public int getNbElecteursApres() {
    return nb_electeurs_ap_rev_spe;
  }
}
