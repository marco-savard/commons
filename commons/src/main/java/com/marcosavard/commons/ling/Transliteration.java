package com.marcosavard.commons.ling;

public class Transliteration {
  public static final String ALPHA = "\u03b1";
  public static final String BETA = "\u03b2";
  public static final String GAMMA = "\u03b3";
  public static final String DELTA = "\u03b4";
  public static final String EPSILON = "\u03b5";

  public static final String ZETA = "\u03b6";
  public static final String ETA = "\u03b7";
  public static final String HE = "\u03ad";
  public static final String THETA = "\u03b8";

  public static final String IOTA = "\u03b9";
  public static final String KAPPA = "\u03ba";
  public static final String LAMBDA = "\u03bb";
  public static final String MU = "\u03bc";
  public static final String NU = "\u03bd";

  public static final String XI = "\u03be";
  public static final String OMICRON = "\u03bf";
  public static final String PI = "\u03c0";
  public static final String RHO = "\u03c1";
  public static final String SIGMA = "\u03c3";
  public static final String FINAL_SIGMA = "\u03c2";

  public static final String TAU = "\u03c4";
  public static final String UPSILON = "\u03c5";

  public static final String PHI = "\u03c6";
  public static final String CHI = "\u03c7";
  public static final String PSI = "\u03c8";
  public static final String OMEGA = "\u03c9";

  public static String toGreek(String latin) {

    String greek = latin.replaceAll("ch", CHI);
    greek = greek.replaceAll("ps", PSI);
    greek = greek.replaceAll("ph", PHI);
    greek = greek.replaceAll("th", THETA);

    greek = greek.replaceAll("he", HE);

    greek = greek.replaceAll("a", ALPHA);
    greek = greek.replaceAll("b", BETA);
    greek = greek.replaceAll("d", DELTA);
    greek = greek.replaceAll("e", EPSILON);
    greek = greek.replaceAll("g", GAMMA);
    greek = greek.replaceAll("i", IOTA);
    greek = greek.replaceAll("k", KAPPA);
    greek = greek.replaceAll("l", LAMBDA);
    greek = greek.replaceAll("m", MU);
    greek = greek.replaceAll("n", NU);
    greek = greek.replaceAll("o", OMICRON);
    greek = greek.replaceAll("p", PI);
    greek = greek.replaceAll("r", RHO);
    greek = greek.replaceAll("s", SIGMA);
    greek = greek.replaceAll("t", TAU);
    greek = greek.replaceAll("u", UPSILON);
    greek = greek.replaceAll("z", ZETA);

    // final sigma
    if (greek.endsWith(SIGMA)) {
      int len = greek.length();
      greek = greek.substring(0, len - 1) + FINAL_SIGMA;
    }

    return greek;
  }

  public static String toLatin(String greek) {
    String latin = greek.replaceAll(CHI, "ch");
    latin = latin.replaceAll(PSI, "ps");
    latin = latin.replaceAll(PHI, "ph");
    latin = latin.replaceAll(THETA, "th");

    latin = latin.replaceAll(HE, "he");


    latin = latin.replaceAll(ALPHA, "a");
    latin = latin.replaceAll(BETA, "b");
    latin = latin.replaceAll(DELTA, "d");
    latin = latin.replaceAll(EPSILON, "e");

    latin = latin.replaceAll(GAMMA, "g");
    latin = latin.replaceAll(IOTA, "i");
    latin = latin.replaceAll(KAPPA, "k");
    latin = latin.replaceAll(LAMBDA, "l");

    latin = latin.replaceAll(MU, "m");
    latin = latin.replaceAll(NU, "n");
    latin = latin.replaceAll(OMICRON, "o");
    latin = latin.replaceAll(PI, "p");
    latin = latin.replaceAll(RHO, "r");
    latin = latin.replaceAll(SIGMA, "s");
    latin = latin.replaceAll(FINAL_SIGMA, "s");
    latin = latin.replaceAll(TAU, "t");
    latin = latin.replaceAll(UPSILON, "u");
    latin = latin.replaceAll(ZETA, "z");

    return latin;
  }



}
