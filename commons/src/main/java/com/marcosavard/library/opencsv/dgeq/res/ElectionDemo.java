package com.marcosavard.library.opencsv.dgeq.res;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.res.CsvResourceFile;
import com.marcosavard.commons.util.ArrayUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://dgeq.org/donnees.html
public class ElectionDemo {

  public static void main(String args[]) {
    // get data
    CsvResourceFile<Circonscription> circonscriptionFile = new Circonscription.CsvFile();
    CsvResourceFile<ElecteurInscrit> electeurFile = new ElecteurInscrit.ElecteurInscritFile();

    List<Circonscription> circonscriptions = circonscriptionFile.readAll();
    List<ElecteurInscrit> electeurs = electeurFile.readAll();

    // filter by region
    int[] circonscriptionsQc = getCirconscriptions(circonscriptions, 3);
    int[] circonscriptionsVilleQc = ArrayUtil.removeAll(circonscriptionsQc, new int[] {760, 714});
    int[] circonscriptionsMtl = getCirconscriptions(circonscriptions, 6);
    int[] circonscriptionsLaval = getCirconscriptions(circonscriptions, 13);

    // get nb electeurs
    List<ElecteurInscrit> electeursQc =
        electeurs.stream()
            .filter(
                e -> Arrays.binarySearch(circonscriptionsVilleQc, e.getCodeCirconscription()) >= 0)
            .collect(Collectors.toList());
    List<ElecteurInscrit> electeursMtl =
        electeurs.stream()
            .filter(e -> Arrays.binarySearch(circonscriptionsMtl, e.getCodeCirconscription()) >= 0)
                .collect(Collectors.toList());
    List<ElecteurInscrit> electeursLaval =
        electeurs.stream()
            .filter(
                e -> Arrays.binarySearch(circonscriptionsLaval, e.getCodeCirconscription()) >= 0)
                .collect(Collectors.toList());

    double nbElecteurs =
        electeurs.stream().mapToDouble(e -> e.getNbElecteursApres()).average().orElse(0.0);

    double nbElecteursQc =
        electeursQc.stream().mapToDouble(e -> e.getNbElecteursApres()).average().orElse(0.0);

    double nbElecteursMtl =
        electeursMtl.stream().mapToDouble(e -> e.getNbElecteursApres()).average().orElse(0.0);

    double nbElecteursLaval =
        electeursLaval.stream().mapToDouble(e -> e.getNbElecteursApres()).average().orElse(0.0);

    Console.println(electeurs.size() + " electeurs");
    Console.println("Electeurs/circonscriptions au Quebec : " + nbElecteurs);
    Console.println("Electeurs/circonscriptions ville de Quebec : " + nbElecteursQc);
    Console.println("Electeurs/circonscriptions ville de Montreal : " + nbElecteursMtl);
    Console.println("Electeurs/circonscriptions ville de Laval : " + nbElecteursLaval);
  }

  private static int[] getCirconscriptions(List<Circonscription> circonscriptions, int region) {
    return circonscriptions.stream()
        .filter(c -> c.getRegion1() == region)
        .mapToInt(c -> c.getBsq())
        .sorted()
        .toArray();
  }
}
