package com.marcosavard.library.opencsv.dgeq.res;

import com.marcosavard.commons.res.CsvResourceFile;

import java.nio.charset.StandardCharsets;

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

  public static class ElecteurInscritFile extends CsvResourceFile<ElecteurInscrit> {

    protected ElecteurInscritFile() {
      super("electeur_inscrit.csv", StandardCharsets.ISO_8859_1, ElecteurInscrit.class);
    }
  }
}
