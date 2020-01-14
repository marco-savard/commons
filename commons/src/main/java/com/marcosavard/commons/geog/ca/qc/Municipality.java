package com.marcosavard.commons.geog.ca.qc;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Municipality {
  public enum Designation {
    CANTON, CANTONS_UNIS, MUNICIPALITE, PAROISSE, VILLAGE, VILLE, UNKNOWN
  };

  private String mcode = "?";
  private String munnom = "?";
  private String mdes = "?";
  private String regadm = "?";
  private String divrec = "?";
  private String mtel = "?";;
  private String mcodpos = "?";;

  public static Municipality of(String name) {
    Municipality municipality = new Municipality(name);
    return municipality;
  }

  public Municipality() {}

  private Municipality(String name) {
    this.munnom = normalize(name);
  }

  public String getCode() {
    return mcode;
  }

  public String getName() {
    return munnom;
  }

  public String getSearchName() {
    String searchName = searchText(munnom);
    return searchName;
  }

  public Designation getDesignation() {
    String searchName = searchText(mdes);
    List<Designation> designations = Arrays.asList(Designation.values());
    Designation designation =
        designations.stream().filter(d -> searchText(d.name()).equalsIgnoreCase(searchName))
            .findFirst().orElse(Designation.UNKNOWN);
    return designation;
  }

  public String getNoTelephone() {
    return mtel;
  }

  public String getPostalCode() {
    return mcodpos;
  }

  public int getRegionAdmin() {
    int i1 = regadm.indexOf('(');
    int i2 = regadm.indexOf(')');
    String number = (i1 == -1) ? "0" : regadm.substring(i1 + 1, i2);
    int regionCode = Integer.parseInt(number);
    return regionCode;
  }

  public String getDivisionRegionale() {
    int idx = divrec.lastIndexOf("(");
    String regionCode = (idx == -1) ? "0" : divrec.substring(idx + 1, idx + 3);
    return regionCode;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Municipality) {
      Municipality that = (Municipality) other;
      String thisText = searchText(this.munnom);
      String thatText = searchText(that.munnom);
      equal = (thisText.equalsIgnoreCase(thatText));
    }

    return equal;
  }

  @Override
  public int hashCode() {
    return this.munnom.hashCode();
  }

  @Override
  public String toString() {
    String str = capitalizeWords(munnom);
    return str;
  }

  private static String searchText(String original) {
    String searchText = Normalizer.normalize(original, Normalizer.Form.NFD);
    searchText = searchText.replaceAll("[^\\p{ASCII}]", "");
    searchText = searchText.replaceAll("Mont-", "Mt-");
    searchText = searchText.replaceAll("Mont ", "Mt ");
    searchText = searchText.replaceAll("Mount-", "Mt-");
    searchText = searchText.replaceAll("Mount ", "Mt ");
    searchText = searchText.replaceAll("Saint-", "St-");
    searchText = searchText.replaceAll("Saint ", "St ");
    searchText = searchText.replaceAll("Sainte-", "Ste-");
    searchText = searchText.replaceAll("Sainte ", "Ste ");
    searchText = searchText.replaceAll("L'", "");
    searchText = searchText.replaceAll("-", "");
    searchText = searchText.replaceAll("_", "");
    searchText = searchText.replaceAll(" ", "");
    return searchText;
  }

  private static String normalize(String original) {
    String normalized = Normalizer.normalize(original, Normalizer.Form.NFD);
    normalized = normalized.replaceAll("Isle", "Île");
    normalized = normalized.replaceAll("Mnt\\.", "Mount");
    normalized = normalized.replaceAll("Mt", "Mont");
    normalized = normalized.replaceAll("St\\.", "Saint");
    normalized = normalized.replaceAll("St-", "Saint-");
    normalized = normalized.replaceAll("Ste-", "Sainte-");
    normalized = normalized.replaceAll("_", "");
    normalized = normalized.trim();
    normalized = capitalizeWords(normalized.toLowerCase());
    return normalized;
  }

  private static final String WORD_SEPARATORS = "-.\'";

  private static String capitalizeWords(String original) {
    List<String> tokens = tokenize(original);
    boolean firstWord = true;

    StringBuilder builder = new StringBuilder();
    for (String token : tokens) {
      int len = token.length();
      boolean capitalize = firstWord || (len > 3);

      if (capitalize) {
        builder.append(Character.toUpperCase(token.charAt(0)));
        builder.append(token.substring(1));
      } else {
        builder.append(token);
      }

      firstWord = false;
    }

    return builder.toString();
  }

  private static List<String> tokenize(String original) {
    List<String> words = new ArrayList<>();
    char[] chars = original.toCharArray();
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];

      if (Character.isLetter(c)) {
        builder.append(c);
      } else {
        words.add(builder.toString());
        words.add(Character.toString(c));
        builder.setLength(0);
      }
    }

    words.add(builder.toString());

    return words;
  }



}
