package com.marcosavard.library.opencsv.dgeq.res;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.res.CsvResourceFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

// https://dgeq.org/donnees.html
public class ElecteurInscritDemo {

  public static void main(String args[]) {
    CsvResourceFile<ElecteurInscrit> resourceFile = new ElecteurInscritFile();
    List<ElecteurInscrit> electeurs = resourceFile.readAll();
    int[] array1 = new int[] {702, 708, 720, 726, 730, 736, 742, 748, 754, 736};

    List<ElecteurInscrit> electeursQc =
        electeurs.stream()
            .filter(e -> Arrays.binarySearch(array1, e.getCodeCirconscription()) >= 0)
            .toList();

    double nbElecteurs =
        electeurs.stream().mapToDouble(e -> e.getNbElecteursApres()).average().orElse(0.0);

    double nbElecteursQc =
        electeursQc.stream().mapToDouble(e -> e.getNbElecteursApres()).average().orElse(0.0);

    Console.println(electeurs.size() + " electeurs");
    Console.println(nbElecteurs + " nbElecteurs");
    Console.println(nbElecteursQc + " nbElecteursQc");
  }

  private static class ElecteurInscritFile extends CsvResourceFile<ElecteurInscrit> {

    protected ElecteurInscritFile() {
      super("electeur_inscrit.csv", StandardCharsets.ISO_8859_1, ElecteurInscrit.class);
    }
  }
}
