package com.marcosavard.library.opencsv.dgeq.res;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.res.CsvResourceFile;

import java.util.Arrays;
import java.util.List;

// https://dgeq.org/donnees.html
public class ElectionDemo {

  public static void main(String args[]) {
    //get data
    CsvResourceFile<Circonscription> circonscriptionFile = new Circonscription.CsvFile();
    CsvResourceFile<ElecteurInscrit> electeurFile = new ElecteurInscrit.ElecteurInscritFile();

    List<Circonscription> circonscriptions = circonscriptionFile.readAll();
    List<ElecteurInscrit> electeurs = electeurFile.readAll();

    //filter by region
    int[] circonscriptionsQc = circonscriptions.stream()
            .filter(c -> c.getRegion1() == 3)
            .mapToInt(c -> c.getBsq())
            .sorted()
            .toArray();

    //get nb electeurs
    List<ElecteurInscrit> electeursQc =
            electeurs.stream()
                    .filter(e -> Arrays.binarySearch(circonscriptionsQc, e.getCodeCirconscription()) >= 0)
                    .toList();

    double nbElecteurs =
        electeurs.stream().mapToDouble(e -> e.getNbElecteursApres()).average().orElse(0.0);

    double nbElecteursQc =
        electeursQc.stream().mapToDouble(e -> e.getNbElecteursApres()).average().orElse(0.0);

    Console.println(electeurs.size() + " electeurs");
    Console.println("Electeurs/circonscriptions au Quebec : " + nbElecteurs);
    Console.println("Electeurs/circonscriptions ville de Quebec : " + nbElecteursQc);
  }


}
