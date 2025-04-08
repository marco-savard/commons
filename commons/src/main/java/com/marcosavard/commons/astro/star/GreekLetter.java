package com.marcosavard.commons.astro.star;

import java.util.stream.Stream;

public enum GreekLetter {
  ALPHA("A", "Alpha"),
  BETA("B", "Beta"),
  GAMMA("C", "Gamma"),
  DELTA("D", "Delta"),
  EPSILON("E", "Epsilon"),
  ZETA("Z", "Zeta"),
  ETA("H", "Eta"),
  THETA("Th", "Theta"),
  IOTA("I", "Iota"),
  KAPPA("K", "Kappa"),
  LAMBDA("L", "Lambda"),
  MU("M", "Mu"),
  NU("N", "Nu"),
  XI("X", "Xi"),
  OMICRON("O", "Omicron"),
  PI("P", "Pi"),
  RHO("R", "Rho"),
  SIGMA("S", "Sigma"),
  TAU("T", "Tau"),
  UPSILON("U", "Upsilon"),
  PHI("Ph", "Phi"),
  CHI("Ch", "Chi"),
  PSI("Ps", "Psi"),
  OMEGA("W", "Omega");

  private String latin;
  private String greekName;

  GreekLetter(String latin, String greekName) {
    this.latin = latin;
    this.greekName = greekName;
  }

  public static GreekLetter of(String latin) {
    Stream<GreekLetter> values = Stream.of(GreekLetter.values());
    GreekLetter letter = values.filter(l -> l.getLatin().equals(latin)).findFirst().orElse(null);
    return letter;
  }

  private String getLatin() {
    return latin;
  }

  String getGreekName() {
    return greekName;
  }
}
